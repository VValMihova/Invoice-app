package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.service.invoice.PdfGenerationService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
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
  
  public InvoicesController( InvoiceService invoiceService, PdfGenerationService pdfService) {
    this.invoiceService = invoiceService;
    this.pdfService = pdfService;
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
    return "view-invoice";
  }
  
  @GetMapping("/edit/{id}")
  public String editInvoice(@PathVariable Long id, Model model) {
    model.addAttribute("invoiceData", this.invoiceService.getById(id));
    return "edit-invoice";
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
    byte[] pdf = pdfService.generatePdf(id, request, response);
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");
    response.getOutputStream().write(pdf);
  }
  
  //  MODEL ATTRIBUTES
  @ModelAttribute("invoiceData")
  public InvoiceEditDto invoiceData() {
    return new InvoiceEditDto();
  }
}

