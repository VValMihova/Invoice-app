package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.model.entity.InvoiceItem;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private final UserHelperService userHelperService;
  private final ModelMapper modelMapper;
  private final CompanyDetailsService companyDetailsService;
  
  public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserHelperService userHelperService, ModelMapper modelMapper, CompanyDetailsService companyDetailsService) {
    this.invoiceRepository = invoiceRepository;
    this.userHelperService = userHelperService;
    this.modelMapper = modelMapper;
    this.companyDetailsService = companyDetailsService;
  }
  
  
//  @Override
//  public void create(InvoiceCreateDto invoiceData) {
//    CompanyDetails recipient = companyDetailsService.getByEik(invoiceData.getRecipient().getEik());
//    CompanyDetails supplier = companyDetailsService.getByEik(invoiceData.getSupplier().getEik());
//    if (recipient == null) {
//      companyDetailsService.add(invoiceData.getRecipient());
//      recipient = companyDetailsService.getByEik(invoiceData.getRecipient().getEik());
//    }
//    Invoice invoice = createInvoice(invoiceData);
//    invoice.setRecipient(recipient);
//    invoice.setSupplier(supplier);
//
//    invoiceRepository.save(invoice);
//  }
  
  @Override
  public List<AllInvoicesView> getAllInvoices() {
    return invoiceRepository.findAllByUserId(userHelperService.getUser().getId())
        .stream().map(invoice -> modelMapper.map(invoice, AllInvoicesView.class))
        .toList();
    
  }
  
  private Invoice createInvoice(InvoiceCreateDto invoiceData) {
    return new Invoice()
        .setInvoiceNumber(invoiceData.getInvoiceNumber())
        .setIssueDate(invoiceData.getIssueDate())
        .setItems(mapToInvoiceItems(invoiceData.getItems()))
        .setTotalAmount(invoiceData.getTotalAmount())
        .setVat(invoiceData.getVat())
        .setAmountDue(invoiceData.getAmountDue())
        .setUser(userHelperService.getUser());
  }
  
  private List<InvoiceItem> mapToInvoiceItems(List<InvoiceItemDto> items) {
    return items.stream().map(invoiceItemDto -> modelMapper.map(invoiceItemDto, InvoiceItem.class))
        .toList();
  }
  
  private CompanyDetails mapToCompanyDetails(CompanyDetailsEditBindingDto companyDetailsDto) {
    return modelMapper.map(companyDetailsDto, CompanyDetails.class);
  }
}
