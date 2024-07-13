package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.model.dto.invoice.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.service.product.ProductService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private final UserHelperService userHelperService;
  private final ModelMapper modelMapper;
  private final CompanyDetailsService companyDetailsService;
  private final RecipientDetailsService recipientDetailsService;
  private final ProductService productService;

  
  public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserHelperService userHelperService, ModelMapper modelMapper, CompanyDetailsService companyDetailsService, RecipientDetailsService recipientDetailsService, ProductService productService) {
    this.invoiceRepository = invoiceRepository;
    this.userHelperService = userHelperService;
    this.modelMapper = modelMapper;
    this.companyDetailsService = companyDetailsService;
    this.recipientDetailsService = recipientDetailsService;
    this.productService = productService;
  }
  
  @Override
  public List<AllInvoicesView> getAllInvoices() {
    return invoiceRepository.findAllByUserId(userHelperService.getUser().getId())
        .stream().map(invoice -> modelMapper.map(invoice, AllInvoicesView.class))
        .toList();
    
  }
  
  @Override
  public void createInvoice(InvoiceCreateDto invoiceData) {
    User currentUser = userHelperService.getUser();
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber());
    invoice.setIssueDate(invoiceData.getIssueDate());
    
    invoice.setSupplier(this.userHelperService.getUserCompanyDetails());
    
    // Setting recipient
    RecipientDetails recipient = getOrCreateRecipientDetails(invoiceData.getRecipientDetails());
    invoice.setRecipient(recipient);
    

    List<InvoiceItem> invoiceItems = mapToInvoiceItems(invoiceData.getItems(),currentUser );
    invoice.setItems(invoiceItems);
    
    invoice.setTotalAmount(invoiceData.getTotalAmount());
    invoice.setVat(invoiceData.getVat());
    invoice.setAmountDue(invoiceData.getAmountDue());
    
    // Setting user

    invoice.setUser(currentUser);
    
    // Save invoice
    invoiceRepository.save(invoice);
  }
  
  @Override
  public boolean checkInvoiceExists(Long invoiceNumber) {
    return this.invoiceRepository
        .findByUserIdAndInvoiceNumber(userHelperService.getUser().getId(), invoiceNumber)
        .isPresent();
  }
  
  private RecipientDetails getOrCreateRecipientDetails(RecipientDetailsAddDto recipientDetailsAddDto) {
    if (recipientDetailsService.exists(mapToRecipientDetails(recipientDetailsAddDto))) {
      return recipientDetailsService.getByVatNumber(recipientDetailsAddDto.getVatNumber());
    } else {
      RecipientDetails newRecipient = modelMapper.map(recipientDetailsAddDto, RecipientDetails.class);
      return recipientDetailsService.saveAndReturn(newRecipient);
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
  
  private CompanyDetails mapToCompanyDetails(CompanyDetailsDto companyDetailsDto) {
    return modelMapper.map(companyDetailsDto, CompanyDetails.class);
  }
  
  private RecipientDetails mapToRecipientDetails(RecipientDetailsAddDto recipientDetailsAddDto) {
    return modelMapper.map(recipientDetailsAddDto, RecipientDetails.class);
  }
}
