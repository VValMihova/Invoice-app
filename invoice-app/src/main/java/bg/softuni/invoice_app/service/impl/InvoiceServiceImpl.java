package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
  
  @Override
  public void createInvoice(InvoiceCreateDto invoiceData) {
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(invoiceData.getInvoiceNumber());
    invoice.setIssueDate(invoiceData.getIssueDate());
    
    invoice.setSupplier(this.userHelperService.getUserCompanyDetails());
    
    // Setting recipient
    CompanyDetails recipient = getOrCreateRecipient(invoiceData.getRecipient());
    invoice.setRecipient(recipient);
    
    // Setting bank account
//    BankAccount bankAccount = modelMapper.map(invoiceData.getBankAccount(), BankAccount.class);
//    bankAccountRepository.save(bankAccount);
//    invoice.setBankAccount(bankAccount);
    
    // Mapping invoice items
    List<InvoiceItem> items = invoiceData.getItems().stream()
        .map(itemDto -> modelMapper.map(itemDto, InvoiceItem.class))
        .collect(Collectors.toList());
    invoice.setItems(items);
    
    invoice.setTotalAmount(invoiceData.getTotalAmount());
    invoice.setVat(invoiceData.getVat());
    invoice.setAmountDue(invoiceData.getAmountDue());
    
    // Setting user
    User currentUser = userHelperService.getUser();
    invoice.setUser(currentUser);
    
    // Save invoice
    invoiceRepository.save(invoice);
  }
  
  private CompanyDetails getOrCreateRecipient(CompanyDetailsDto recipientDto) {
    if (companyDetailsService.exists(mapToCompanyDetails(recipientDto))) {
      return companyDetailsService.getByEik(recipientDto.getEik());
    } else {
      CompanyDetails newRecipient = modelMapper.map(recipientDto, CompanyDetails.class);
      return companyDetailsService.saveAndReturn(newRecipient);
    }
  }

//  private Invoice createInvoice(InvoiceCreateDto invoiceData) {
//    return new Invoice()
//        .setInvoiceNumber(invoiceData.getInvoiceNumber())
//        .setIssueDate(invoiceData.getIssueDate())
//        .setItems(mapToInvoiceItems(invoiceData.getItems()))
//        .setTotalAmount(invoiceData.getTotalAmount())
//        .setVat(invoiceData.getVat())
//        .setAmountDue(invoiceData.getAmountDue())
//        .setUser(userHelperService.getUser());
//  }
  
  private List<InvoiceItem> mapToInvoiceItems(List<InvoiceItemDto> items) {
    return items.stream().map(invoiceItemDto -> modelMapper.map(invoiceItemDto, InvoiceItem.class))
        .toList();
  }
  
  private CompanyDetails mapToCompanyDetails(CompanyDetailsDto companyDetailsDto) {
    return modelMapper.map(companyDetailsDto, CompanyDetails.class);
  }
}
