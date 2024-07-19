package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.invoice.PdfGenerationService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/invoices")
public class InvoicesController {
  
  private final InvoiceService invoiceService;
  private final PdfGenerationService pdfService;
  private final RecipientDetailsService recipientDetailsService;
  private final BankAccountService bankAccountService;
  private final UserService userService;
  
  public InvoicesController(
      InvoiceService invoiceService,
      PdfGenerationService pdfService,
      RecipientDetailsService recipientDetailsService,
      BankAccountService bankAccountService,
      UserService userService) {
    this.invoiceService = invoiceService;
    this.pdfService = pdfService;
    this.recipientDetailsService = recipientDetailsService;
    this.bankAccountService = bankAccountService;
    this.userService = userService;
  }
  
  
  @GetMapping
  public String viewInvoices(Model model) {
    model.addAttribute("invoices", invoiceService.getAllInvoices());
    return "invoices";
  }
  
  @GetMapping("/view/{id}")
  public String viewInvoice(@PathVariable Long id, Model model) {
    InvoiceView invoiceView = invoiceService.getById(id);
    model.addAttribute("invoice", invoiceView);
    return "invoice-view";
  }
  
  @GetMapping("/edit/{id}")
  public String editInvoice(@PathVariable Long id, Model model) {
    model.addAttribute("bankAccounts",
        this.bankAccountService.findAllForCompany(this.userService.showCompanyDetails().getId()));
    model.addAttribute("invoiceData", this.invoiceService.getById(id));
    return "invoice-edit";
  }
  
  //  todo add validation for unique or the same invoice number
  @PostMapping("/edit/{id}")
  public String updateInvoice(@PathVariable Long id,
                              @Valid InvoiceEditDto invoiceData,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
    
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("invoiceData", invoiceData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoiceData", bindingResult);
      return "redirect:/invoices/edit/" + id;
    }
    
    invoiceService.updateInvoice(id, invoiceData);
    return "redirect:/invoices";
  }
  
  @PostMapping("/delete/{id}")
  public String deleteInvoice(@PathVariable Long id) {
    invoiceService.deleteById(id);
    return "redirect:/invoices";
  }
  
  
  @GetMapping("/download-pdf/{id}")
  public void downloadPdf(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
    byte[] pdf = pdfService.generateInvoicePdf(id, request, response);
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");
    response.getOutputStream().write(pdf);
  }
  
  @GetMapping("/create-with-client/{clientId}")
  public String createInvoiceWithClient(@PathVariable Long clientId, Model model) {
    RecipientDetailsView recipientDetailsView = recipientDetailsService.findById(clientId);
    
    model.addAttribute("recipient", recipientDetailsView);
    model.addAttribute("bankAccounts",
        this.bankAccountService.findAllForCompany(this.userService.showCompanyDetails().getId()));
    
    return "invoice-create-with-client";
  }
  
  @PostMapping("/create-with-client/{clientId}")
  public String createInvoiceWithClient(@PathVariable Long clientId,
                                        @Valid InvoiceCreateDto invoiceData,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
    
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("invoiceData", invoiceData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoiceData", bindingResult);
      return "redirect:/invoices/create-with-client/" + clientId;
    }
    
    invoiceService.createInvoiceWithClient(clientId, invoiceData);
    return "redirect:/invoices";
  }
  
  
  //  MODEL ATTRIBUTES
  @ModelAttribute("invoiceData")
  public InvoiceEditDto invoiceData() {
    return new InvoiceEditDto();
  }
}
  
