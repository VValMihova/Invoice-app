package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.binding.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.service.InvoiceService;
import bg.softuni.invoice_app.service.impl.UserHelperService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
  private final InvoiceService invoiceService;
  private final UserHelperService userHelperService;
  
  public InvoiceController(InvoiceService invoiceService, UserHelperService userHelperService) {
    this.invoiceService = invoiceService;
    this.userHelperService = userHelperService;
  }
  
  @GetMapping("/create")
  public ModelAndView createInvoice() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("supplierDetails",userHelperService.getCompanyDetails());
    //modelAndView.addObject("bankAccounts", userHelperService.getBankAccounts());
    
    modelAndView.setViewName("invoice-create");
    return modelAndView;
  }
//  @Transactional
//  @PostMapping("/create")
//  public String doCreateInvoice(
//      @Valid InvoiceCreateDto invoiceData,
//      BindingResult bindingResult,
//      RedirectAttributes redirectAttributes) {
//
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.addObject("invoiceData");
//    if (bindingResult.hasErrors()) {
//      redirectAttributes.addFlashAttribute("invoiceData", invoiceData);
//      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoiceData", bindingResult);
//      return "redirect:/invoice-create";
//    }
//
//    this.invoiceService.create(invoiceData);
//
//    return "redirect:/profile";
//  }
  
  //  MODEL ATTRIBUTES
  @ModelAttribute
  public InvoiceCreateDto invoiceData() {
    return new InvoiceCreateDto();
  }
}
