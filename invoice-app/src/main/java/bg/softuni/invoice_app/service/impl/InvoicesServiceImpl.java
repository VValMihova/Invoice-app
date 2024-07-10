package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.model.entity.InvoiceItem;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.service.InvoicesService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoicesServiceImpl implements InvoicesService {
  
  private final InvoiceRepository invoiceRepository;
  private final CompanyDetailsService companyDetailsService;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  
  public InvoicesServiceImpl(InvoiceRepository invoiceRepository, CompanyDetailsService companyDetailsService, UserRepository userRepository, ModelMapper modelMapper) {
    this.invoiceRepository = invoiceRepository;
    this.companyDetailsService = companyDetailsService;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
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

  
//  @Override
//  public void update(Long id, InvoiceCreateDto invoiceData) {
//    Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
//
//    CompanyDetails recipient = companyDetailsService.getByEik(invoiceData.getRecipient().getEik());
//    CompanyDetails supplier = companyDetailsService.getByEik(invoiceData.getSupplier().getEik());
//    if (recipient == null) {
//      companyDetailsService.add(invoiceData.getRecipient());
//      recipient = companyDetailsService.getByEik(invoiceData.getRecipient().getEik());
//    }
//    existingInvoice.setInvoiceNumber(invoiceData.getInvoiceNumber());
//    existingInvoice.setIssueDate(invoiceData.getIssueDate());
//    existingInvoice.setSupplier(supplier);
//    existingInvoice.setRecipient(recipient);
//    updateInvoiceItems(existingInvoice, invoiceData.getItems());
//    existingInvoice.setTotalAmount(invoiceData.getTotalAmount());
//    existingInvoice.setVat(invoiceData.getVat());
//    existingInvoice.setAmountDue(invoiceData.getAmountDue());
//
//    invoiceRepository.save(existingInvoice);
//  }
//
  private void updateInvoiceItems(Invoice existingInvoice, List<InvoiceItemDto> newItems) {
    existingInvoice.getItems().clear();
    
    for (InvoiceItemDto itemDto : newItems) {
      InvoiceItem item = modelMapper.map(itemDto, InvoiceItem.class);
      existingInvoice.getItems().add(item);
    }
  }
  private List<InvoiceItem> mapToInvoiceItems(List<InvoiceItemDto> items) {
    return items.stream().map(invoiceItemDto -> modelMapper.map(invoiceItemDto, InvoiceItem.class))
        .collect(Collectors.toList());
  }

  private CompanyDetails mapToCompanyDetails(CompanyDetailsEditBindingDto companyDetailsDto) {
    return modelMapper.map(companyDetailsDto, CompanyDetails.class);
  }
}

