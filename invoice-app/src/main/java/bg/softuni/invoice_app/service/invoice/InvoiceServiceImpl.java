package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.invoice.*;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private final ModelMapper modelMapper;
  private final RecipientDetailsService recipientDetailsService;
  private final BankAccountService bankAccountService;
  private final UserService userService;
  private final SaleService saleService;
  
  
  public InvoiceServiceImpl(
      InvoiceRepository invoiceRepository,
      ModelMapper modelMapper,
      RecipientDetailsService recipientDetailsService,
      BankAccountService bankAccountService,
      UserService userService,
      SaleService saleService) {
    this.invoiceRepository = invoiceRepository;
    this.modelMapper = modelMapper;
    this.recipientDetailsService = recipientDetailsService;
    this.bankAccountService = bankAccountService;
    this.userService = userService;
    this.saleService = saleService;
  }
  
  @Override
  public List<AllInvoicesView> getAllInvoices() {
    return invoiceRepository.findAllByUserId(userService.getCurrentUserId())
        .stream().map(invoice -> modelMapper.map(invoice, AllInvoicesView.class))
        .toList();
  }
  
  @Transactional
  @Override
  public void updateInvoice(Long id, InvoiceEditDto invoiceData) {
    User currentUser = userService.getUser();
    Invoice invoice = invoiceRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Invoice"));
    
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber())
        .setIssueDate(invoiceData.getIssueDate())
        .setSupplier(userService.getCompanyDetails());
    
    BankAccount bankAccount = bankAccountService.getByIban(invoiceData.getBankAccount());
    invoice.setBankAccount(bankAccount);
    
    List<InvoiceItem> updatedItems = mapToInvoiceItems(invoiceData.getItems());
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
          .setInvoiceId(invoice.getId())
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
  
  @Override
  public void deleteById(Long id) {
    Invoice invoice = findByIdOrThrow(id);
    invoiceRepository.delete(invoice);
  }
  
  @Override
  public InvoiceView getById(Long id) {
    Invoice invoice = findByIdOrThrow(id);
    return mapToInvoiceView(invoice);
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
    
    List<InvoiceItem> invoiceItems = mapToInvoiceItems(invoiceData.getItems());
    invoice.setItems(invoiceItems)
        .setTotalAmount(invoiceData.getTotalAmount())
        .setVat(invoiceData.getVat())
        .setAmountDue(invoiceData.getAmountDue());
    
    BankAccount bankAccount = bankAccountService.getByIban(invoiceData.getBankAccount());
    invoice.setBankAccount(bankAccount)
        .setUser(currentUser);
    
    invoiceRepository.save(invoice);
    addSales(invoiceData, invoiceItems, currentUser);
  }
  
  private void addSales(InvoiceCreateDto invoiceData, List<InvoiceItem> invoiceItems, User currentUser) {
    for (InvoiceItem invoiceItem : invoiceItems) {
      Sale sale = new Sale()
          .setSaleDate(invoiceData.getIssueDate())
          .setProductName(invoiceItem.getName())
          .setQuantity(invoiceItem.getQuantity())
          .setInvoiceId(invoiceData.getInvoiceNumber())
          .setUser(currentUser);
      saleService.save(sale);
    }
  }
  
  private List<InvoiceItem> mapToInvoiceItems(List<InvoiceItemDto> items) {
    List<InvoiceItem> invoiceItems = new ArrayList<>();
    for (InvoiceItemDto itemDto : items) {
      InvoiceItem invoiceItem = modelMapper.map(itemDto, InvoiceItem.class);
      invoiceItems.add(invoiceItem);
    }
    return invoiceItems;
  }
  
  private InvoiceView mapToInvoiceView(Invoice invoice) {
    return new InvoiceView()
        .setId(invoice.getId())
        .setInvoiceNumber(invoice.getInvoiceNumber())
        .setIssueDate(invoice.getIssueDate())
        .setSupplier(mapToCompanyDetailsView(invoice.getSupplier()))
        .setRecipient(mapToRecipientDetailsView(invoice.getRecipient()))
        .setBankAccount(mapToBankAccountView(invoice.getBankAccount()))
        .setAmountDue(invoice.getAmountDue())
        .setVat(invoice.getVat())
        .setItems(invoice.getItems().stream()
            .map(this::mapToInvoiceItemView)
            .collect(Collectors.toList()))
        .setTotalAmount(invoice.getTotalAmount());
  }
  
  private Invoice findByIdOrThrow(Long id) {
    return this.invoiceRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Invoice"));
  }
  
  private BankAccountView mapToBankAccountView(BankAccount bankAccount) {
    return new BankAccountView(bankAccount);
  }
  
  private InvoiceItemView mapToInvoiceItemView(InvoiceItem invoiceItem) {
    return new InvoiceItemView(invoiceItem);
  }
  
  private RecipientDetailsView mapToRecipientDetailsView(RecipientDetails recipient) {
    return new RecipientDetailsView(recipient);
  }
  
  private CompanyDetailsView mapToCompanyDetailsView(CompanyDetails supplier) {
    return new CompanyDetailsView(supplier);
  }
}
