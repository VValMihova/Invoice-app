package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import bg.softuni.invoice_app.model.dto.invoice.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.service.InvoicesService;
import bg.softuni.invoice_app.service.ProductService;
import bg.softuni.invoice_app.service.RecipientDetailsService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoicesServiceImpl implements InvoicesService {
  
  private final InvoiceRepository invoiceRepository;
  private final CompanyDetailsService companyDetailsService;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final UserHelperService userHelperService;
  private final RecipientDetailsService recipientDetailsService;
  private final ProductService productService;
  
  public InvoicesServiceImpl(InvoiceRepository invoiceRepository, CompanyDetailsService companyDetailsService, UserRepository userRepository, ModelMapper modelMapper, UserHelperService userHelperService, RecipientDetailsService recipientDetailsService, ProductService productService) {
    this.invoiceRepository = invoiceRepository;
    this.companyDetailsService = companyDetailsService;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.userHelperService = userHelperService;
    this.recipientDetailsService = recipientDetailsService;
    this.productService = productService;
  }
  
  @Override
  public Optional<Invoice> findById(Long id) {
    return invoiceRepository.findById(id);
  }

  @Override
  public void deleteById(Long id) {
    invoiceRepository.deleteById(id);
  }
  
  @Override
  public byte[] generatePdf(Long id) {
    Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
    
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      Document document = new Document();
      PdfWriter.getInstance(document, byteArrayOutputStream);
      document.open();
      
      // Примерно съдържание на PDF документа
      document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
      document.add(new Paragraph("Issue Date: " + invoice.getIssueDate().toString()));
      document.add(new Paragraph("Supplier: " + invoice.getSupplier().getCompanyName()));
      document.add(new Paragraph("Recipient: " + invoice.getRecipient().getCompanyName()));
      document.add(new Paragraph("Total Amount: " + invoice.getTotalAmount().toString()));
      
      // Добавете повече съдържание според нуждите
      
      document.close();
      
      return byteArrayOutputStream.toByteArray();
    } catch (DocumentException | IOException e) {
      throw new RuntimeException("Failed to generate PDF", e);
    }
  }
  
  @Override
  public void updateInvoice(Long id, InvoiceEditDto invoiceData) {
    User currentUser = userHelperService.getUser();
    Invoice invoice = invoiceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + id));
    
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber());
    invoice.setIssueDate(invoiceData.getIssueDate());
    invoice.setSupplier(userHelperService.getUserCompanyDetails());
    
    // Setting recipient
    RecipientDetails recipient = getOrCreateRecipientDetails(invoiceData.getRecipient());
    invoice.setRecipient(recipient);
    
    // Update invoice items
    List<InvoiceItem> updatedItems = mapToInvoiceItems(invoiceData.getItems(), currentUser);
    invoice.getItems().clear();
    invoice.getItems().addAll(updatedItems);
    
    invoice.setTotalAmount(invoiceData.getTotalAmount());
    invoice.setVat(invoiceData.getVat());
    invoice.setAmountDue(invoiceData.getAmountDue());
    
    // Save invoice
    invoiceRepository.save(invoice);
  
  }
  
  private RecipientDetails getOrCreateRecipientDetails(RecipientDetailsAddDto recipientDetailsAddDto) {
    if (recipientDetailsService.exists(mapToRecipientDetails(recipientDetailsAddDto))) {
      return recipientDetailsService.getByVatNumber(recipientDetailsAddDto.getVatNumber());
    } else {
      RecipientDetails newRecipient = modelMapper.map(recipientDetailsAddDto, RecipientDetails.class);
      return recipientDetailsService.saveAndReturn(newRecipient);
    }
  }
  
  private RecipientDetails mapToRecipientDetails(RecipientDetailsAddDto recipientDetails) {
    return modelMapper.map(recipientDetails, RecipientDetails.class);
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
  
  
  private CompanyDetails mapToCompanyDetails(CompanyDetailsEditBindingDto companyDetailsDto) {
    return modelMapper.map(companyDetailsDto, CompanyDetails.class);
  }
}

