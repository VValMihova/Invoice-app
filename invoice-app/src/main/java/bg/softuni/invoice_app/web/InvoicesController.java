package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.binding.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.service.InvoiceService;
import bg.softuni.invoice_app.service.InvoicesService;
import bg.softuni.invoice_app.service.impl.UserHelperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invoices")
public class InvoicesController {
  
  private final InvoicesService invoicesService;
  private final InvoiceService invoiceService;
  
  public InvoicesController(InvoicesService invoicesService, InvoiceService invoiceService) {
    this.invoicesService = invoicesService;
    this.invoiceService = invoiceService;
  }
  
  
  @GetMapping
  public String viewInvoices(Model model) {
    model.addAttribute("invoices", invoiceService.getAllInvoices());
    return "invoices";
  }
  
  @GetMapping("/edit/{id}")
  public String editInvoice(@PathVariable Long id, Model model) {
    Invoice invoice = invoicesService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
    model.addAttribute("invoice", invoice);
    return "edit-invoice";
  }
  
//  @PostMapping("/edit/{id}")
//  public String updateInvoice(
//      @PathVariable Long id,
//      @Valid InvoiceCreateDto invoiceData,
//      BindingResult bindingResult,
//      RedirectAttributes redirectAttributes) {
//    if (bindingResult.hasErrors()) {
//      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoice", bindingResult);
//      redirectAttributes.addFlashAttribute("invoice", invoiceData);
//      return "redirect:/invoices/edit/" + id;
//    }
//
//    invoicesService.update(id, invoiceData);
//    return "redirect:/invoices";
//  }
  
  @PostMapping("/delete/{id}")
  public String deleteInvoice(@PathVariable Long id) {
    invoicesService.deleteById(id);
    return "redirect:/invoices";
  }
  
  
  @GetMapping("/download-pdf/{id}")
  public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
    byte[] pdfContent = invoicesService.generatePdf(id);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", "invoice.pdf");
    headers.setContentLength(pdfContent.length);
    
    return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
  }
  
//  MODEL ATTRIBUTES
  @ModelAttribute
  public InvoiceCreateDto invoiceData() {
    return new InvoiceCreateDto();
  }
}

