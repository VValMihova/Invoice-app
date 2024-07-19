package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.invoice.*;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.product.ProductService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
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
  private final ProductService productService;
  private final BankAccountService bankAccountService;
  private final UserService userService;
  
  
  public InvoiceServiceImpl(
      InvoiceRepository invoiceRepository,
      ModelMapper modelMapper,
      RecipientDetailsService recipientDetailsService,
      ProductService productService, BankAccountService bankAccountService, UserService userService) {
    this.invoiceRepository = invoiceRepository;
    this.modelMapper = modelMapper;
    this.recipientDetailsService = recipientDetailsService;
    this.productService = productService;
    this.bankAccountService = bankAccountService;
    this.userService = userService;
  }
  
  @Override
  public List<AllInvoicesView> getAllInvoices() {
    return invoiceRepository.findAllByUserId(userService.getCurrentUserId())
        .stream().map(invoice -> modelMapper.map(invoice, AllInvoicesView.class))
        .toList();
  }
  
  
  @Override
  public void updateInvoice(Long id, InvoiceEditDto invoiceData) {
    User currentUser = userService.getUser();
    Invoice invoice = invoiceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + id));
    
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber());
    invoice.setIssueDate(invoiceData.getIssueDate());
    invoice.setSupplier(userService.getCompanyDetails());
    
    BankAccount bankAccount = bankAccountService.getByIban(invoiceData.getBankAccount());
    invoice.setBankAccount(bankAccount);
    
    List<InvoiceItem> updatedItems = mapToInvoiceItems(invoiceData.getItems(), currentUser);
    invoice.getItems().clear();
    invoice.getItems().addAll(updatedItems);
    
    invoice.setTotalAmount(invoiceData.getTotalAmount());
    invoice.setVat(invoiceData.getVat());
    invoice.setAmountDue(invoiceData.getAmountDue());
    
    invoiceRepository.save(invoice);
    
  }
  
  @Override
  public boolean checkInvoiceExists(Long invoiceNumber) {
    return this.invoiceRepository
        .findByUserIdAndInvoiceNumber(userService.getCurrentUserId(), invoiceNumber)
        .isPresent();
  }
  
  //todo can add exception
  @Override
  public void deleteById(Long id) {
    invoiceRepository.deleteById(id);
  }
  
  @Override
  public InvoiceView getById(Long id) {
    Invoice invoice = this.invoiceRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Invoice"));
    
    return mapToInvoiceView(invoice);
  }
  
  @Override
  public void createInvoiceWithClient(Long clientId, InvoiceCreateDto invoiceData) {
    User currentUser = userService.getUser();
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber());
    invoice.setIssueDate(invoiceData.getIssueDate());
    
    invoice.setSupplier(userService.getCompanyDetails());
    
    // Setting recipient
    RecipientDetails recipient = recipientDetailsService.getById(clientId);
    invoice.setRecipient(recipient);
    
    List<InvoiceItem> invoiceItems = mapToInvoiceItems(invoiceData.getItems(), currentUser);
    invoice.setItems(invoiceItems);
    
    invoice.setTotalAmount(invoiceData.getTotalAmount());
    invoice.setVat(invoiceData.getVat());
    invoice.setAmountDue(invoiceData.getAmountDue());
    
    BankAccount bankAccount = bankAccountService.getByIban(invoiceData.getBankAccount());
    invoice.setBankAccount(bankAccount);
    
    invoice.setUser(currentUser);
    
    invoiceRepository.save(invoice);
  }
  
  
  private void updateInvoiceItems(Invoice existingInvoice, List<InvoiceItemDto> newItems) {
    existingInvoice.getItems().clear();
    
    for (InvoiceItemDto itemDto : newItems) {
      InvoiceItem item = modelMapper.map(itemDto, InvoiceItem.class);
      existingInvoice.getItems().add(item);
    }
  }
  
  private List<InvoiceItem> mapToInvoiceItems(List<InvoiceItemDto> items, User user) {
    List<InvoiceItem> invoiceItems = new ArrayList<>();
    for (InvoiceItemDto itemDto : items) {
      InvoiceItem invoiceItem = modelMapper.map(itemDto, InvoiceItem.class);
      
      Product product = user.getProducts()
          .stream()
          .filter(p -> p.getName().equals(itemDto.getName()))
          .findFirst()
          .orElseGet(() -> {
            Product newProduct = new Product().setName(itemDto.getName());
            newProduct.setUser(user);
            user.getProducts().add(newProduct);
            return newProduct;
          });
      
      if (product.getId() != null) {
        product.setQuantity(product.getQuantity().add(itemDto.getQuantity()));
      } else {
        product.setQuantity(itemDto.getQuantity());
      }
      
      productService.save(product);
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
