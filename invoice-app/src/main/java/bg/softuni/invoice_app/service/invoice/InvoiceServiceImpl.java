package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.InvoiceNotFoundException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.invoice.*;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.bankAccountPersist.BankAccountPersistService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.utils.archive.InvoiceDeletedEvent;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private final ModelMapper modelMapper;
  private final RecipientDetailsService recipientDetailsService;
  private final BankAccountService bankAccountService;
  private final UserService userService;
  private final SaleService saleService;
  private final BankAccountPersistService bankAccountPersistService;
  private final ApplicationEventPublisher eventPublisher;
  
  
  public InvoiceServiceImpl(
      InvoiceRepository invoiceRepository,
      ModelMapper modelMapper,
      RecipientDetailsService recipientDetailsService,
      BankAccountService bankAccountService,
      UserService userService,
      @Lazy SaleService saleService,
      BankAccountPersistService bankAccountPersistService, ApplicationEventPublisher eventPublisher
  ) {
    this.invoiceRepository = invoiceRepository;
    this.modelMapper = modelMapper;
    this.recipientDetailsService = recipientDetailsService;
    this.bankAccountService = bankAccountService;
    this.userService = userService;
    this.saleService = saleService;
    this.bankAccountPersistService = bankAccountPersistService;
    this.eventPublisher = eventPublisher;
  }
  
  @Override
  public List<AllInvoicesView> getAllInvoices() {
    return invoiceRepository.findAllByUserIdOrderByInvoiceNumber(userService.getCurrentUserId())
        .stream().map(invoice -> modelMapper.map(invoice, AllInvoicesView.class))
        .toList();
  }
  
  @Transactional
  @Override
  public void updateInvoice(Long id, InvoiceEditDto invoiceData) {
    User currentUser = userService.getUser();
    Invoice invoice = invoiceRepository.findById(id)
        .orElseThrow(() -> new InvoiceNotFoundException(ErrorMessages.INVOICE_NOT_FOUND));
    
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber())
        .setIssueDate(invoiceData.getIssueDate())
        .setSupplier(userService.getCompanyDetails());
    
    BankAccountView bankAccount = bankAccountService.getViewByIban(invoiceData.getBankAccountIban());
    BankAccountPersist accountPersist =
        bankAccountPersistService.add(bankAccount, currentUser);
    invoice.setBankAccountPersist(accountPersist);
    
    List<InvoiceItem> updatedItems = mapToInvoiceItems(invoiceData.getItems(), invoice);
    invoice.getItems().clear();
    invoice.getItems().addAll(updatedItems);
    
    invoice.setTotalAmount(invoiceData.getTotalAmount())
        .setVat(invoiceData.getVat())
        .setAmountDue(invoiceData.getAmountDue());
    
    invoiceRepository.save(invoice);
    saleService.deleteAllByInvoiceId(invoice.getId());
    for (InvoiceItem updatedItem : updatedItems) {
      Sale sale = new Sale()
          .setSaleDate(invoiceData.getIssueDate())
          .setProductName(updatedItem.getName())
          .setQuantity(updatedItem.getQuantity())
          .setInvoiceNumber(invoice.getInvoiceNumber())
          .setUser(currentUser);
      saleService.save(sale);
    }
  }
  
  @Override
  public boolean checkInvoiceExists(Long invoiceNumber) {
    return this.invoiceRepository
        .findByUserIdAndInvoiceNumber(userService.getCurrentUserId(), invoiceNumber)
        .isPresent();
  }
  
  
  @Transactional
  public void deleteById(Long invoiceId) {
    Invoice invoice = invoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new InvoiceNotFoundException(ErrorMessages.INVOICE_NOT_FOUND));
    eventPublisher.publishEvent(new InvoiceDeletedEvent(this, invoice));
    invoiceRepository.delete(invoice);
    
  }
  
  @Override
  public InvoiceView getById(Long id) {
    Invoice invoice = findByIdOrThrow(id);
    return new InvoiceView(invoice);
  }
  
  @Override
  public void createInvoiceWithClient(Long clientId, InvoiceCreateDto invoiceData) {
    User currentUser = userService.getUser();
    Invoice invoice = new Invoice()
        .setInvoiceNumber(invoiceData.getInvoiceNumber())
        .setIssueDate(invoiceData.getIssueDate())
        .setSupplier(userService.getCompanyDetails());
    
    RecipientDetails recipient = recipientDetailsService.getById(clientId);
    invoice.setRecipient(recipient);
    
    List<InvoiceItem> invoiceItems = mapToInvoiceItems(invoiceData.getItems(), invoice);
    invoice.setItems(invoiceItems)
        .setTotalAmount(invoiceData.getTotalAmount())
        .setVat(invoiceData.getVat())
        .setAmountDue(invoiceData.getAmountDue());
    
    BankAccountView bankAccount = bankAccountService.getViewByIban(invoiceData.getBankAccountIban());
    BankAccountPersist accountPersist = bankAccountPersistService.add(bankAccount, currentUser);
    invoice.setBankAccountPersist(accountPersist)
        .setUser(currentUser);
    
    invoiceRepository.save(invoice);
    addSales(invoiceData, invoiceItems, currentUser);
  }
  
  @Override
  public InvoiceEditDto convertToEditDto(InvoiceView invoiceView) {
    InvoiceEditDto dto = new InvoiceEditDto();
    dto.setId(invoiceView.getId());
    dto.setInvoiceNumber(invoiceView.getInvoiceNumber());
    dto.setIssueDate(invoiceView.getIssueDate());
    dto.setRecipient(invoiceView.getRecipient());
    dto.setBankAccountIban(invoiceView.getBankAccountPersist().getIban());
    dto.setItems(invoiceView.getItems().stream()
        .map(InvoiceItemDto::new)
        .collect(Collectors.toList()));
    dto.setTotalAmount(invoiceView.getTotalAmount());
    dto.setVat(invoiceView.getVat());
    dto.setAmountDue(invoiceView.getAmountDue());
    return dto;
  }
  
  @Override
  public boolean existsByBankAccount(BankAccountPersist account) {
    return invoiceRepository.existsByBankAccountPersist(account);
  }
  
  @Override
  public List<AllInvoicesView> searchInvoices(String recipient, LocalDate issueDate) {
    return invoiceRepository.findAllByUserIdAndCriteria(userService.getCurrentUserId(), recipient, issueDate)
        .stream().map(invoice -> modelMapper.map(invoice, AllInvoicesView.class))
        .toList();
  }
  
  
  private void addSales(InvoiceCreateDto invoiceData, List<InvoiceItem> invoiceItems, User currentUser) {
    for (InvoiceItem invoiceItem : invoiceItems) {
      Sale sale = new Sale()
          .setSaleDate(invoiceData.getIssueDate())
          .setProductName(invoiceItem.getName())
          .setQuantity(invoiceItem.getQuantity())
          .setInvoiceNumber(invoiceData.getInvoiceNumber())
          .setUser(currentUser);
      saleService.save(sale);
    }
  }
  
  private List<InvoiceItem> mapToInvoiceItems(List<InvoiceItemDto> items, Invoice invoice) {
    List<InvoiceItem> invoiceItems = new ArrayList<>();
    for (InvoiceItemDto itemDto : items) {
      InvoiceItem invoiceItem = modelMapper.map(itemDto, InvoiceItem.class).setInvoice(invoice);
      invoiceItems.add(invoiceItem);
    }
    return invoiceItems;
  }
  
  protected Invoice findByIdOrThrow(Long id) {
    return this.invoiceRepository.findById(id)
        .orElseThrow(() -> new InvoiceNotFoundException(ErrorMessages.INVOICE_NOT_FOUND));
  }
  
  @Override
  public boolean isInvoiceNumberUniqueOrSame(Long invoiceId, Long invoiceNumber) {
    Invoice invoice = invoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new InvoiceNotFoundException(ErrorMessages.INVOICE_NOT_FOUND));
    return !invoiceRepository.existsByInvoiceNumber(invoiceNumber)
           || Objects.equals(invoice.getInvoiceNumber(), invoiceNumber);
  }
}
