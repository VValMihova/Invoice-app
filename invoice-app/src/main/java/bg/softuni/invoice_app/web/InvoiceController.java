package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
  private final BankAccountService bankAccountService;
  private final RecipientDetailsService recipientDetailsService;
  private final UserService userService;
  
  public InvoiceController(
      InvoiceService invoiceService,
      BankAccountService bankAccountService,
      RecipientDetailsService recipientDetailsService,
      UserService userService) {
    this.invoiceService = invoiceService;
    this.bankAccountService = bankAccountService;
    this.recipientDetailsService = recipientDetailsService;
    this.userService = userService;
  }
  @GetMapping("/create")
  public ModelAndView createInvoice() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("bankAccounts",
        this.bankAccountService.findAllForCompany(userService.showCompanyDetails().getId()) );

    modelAndView.setViewName("invoice-create");
    return modelAndView;
  }
  
  @PostMapping("/create")
  public String createInvoice(@Valid InvoiceCreateDto invoiceData,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
    if (invoiceData.getItems() == null || invoiceData.getItems().isEmpty()) {
      bindingResult.addError(new FieldError("invoiceData", "items", "Item fields cannot be empty!"));
    }
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("invoiceData", invoiceData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoiceData", bindingResult);
      return "redirect:/invoices/create";
    }
    
    invoiceService.createInvoice(invoiceData);
    return "redirect:/invoices";
  }
  
  //  MODEL ATTRIBUTES
  @ModelAttribute("invoiceData")
  public InvoiceCreateDto invoiceData() {
    return new InvoiceCreateDto();
  }
}
