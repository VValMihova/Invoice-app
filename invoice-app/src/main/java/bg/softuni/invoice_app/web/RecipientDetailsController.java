package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/clients")
public class RecipientDetailsController {
  private final RecipientDetailsService recipientDetailsService;
  private final MessageSource messageSource;
  
  public RecipientDetailsController(
      RecipientDetailsService recipientDetailsService, MessageSource messageSource) {
    this.recipientDetailsService = recipientDetailsService;
    this.messageSource = messageSource;
  }
  
  @GetMapping
  public String clientsPage(
      @RequestParam(value = "companyName", required = false) String companyName,
      @RequestParam(value = "eik", required = false) String eik,
      Model model) {
    List<RecipientDetailsView> clients = recipientDetailsService.searchClients(companyName, eik);
    model.addAttribute("clients", clients);
    return "clients";
  }
  
  @GetMapping("/add")
  public String showAddClientForm() {
    return "client-add";
  }
  
  @PostMapping("/add")
  public String addClient(
      @Valid RecipientDetailsAddDto recipientDetails,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Locale locale) {
    
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("recipientDetails", recipientDetails);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipientDetails", bindingResult);
      return "redirect:/clients/add";
    }
    recipientDetailsService.addRecipientDetails(recipientDetails);
    String successMessage = messageSource.getMessage("success.client.added", null, locale);
    redirectAttributes.addFlashAttribute("successMessage", successMessage);
    return "redirect:/clients";
  }
  
  
  @GetMapping("/edit/{id}")
  public String showEditClientForm(@PathVariable Long id, Model model) {
    if (!model.containsAttribute("recipientDetailsEdit")) {
      RecipientDetails recipientDetails = recipientDetailsService.getById(id);
      model.addAttribute("recipientDetailsEdit", recipientDetails);
    }
    return "client-edit";
  }
  
  @PostMapping("/edit/{id}")
  public String editClient(
      @PathVariable Long id,
      @Valid @ModelAttribute("recipientDetailsEdit") RecipientDetailsEdit recipientDetailsEdit,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("recipientDetailsEdit", recipientDetailsEdit);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipientDetailsEdit", bindingResult);
      return "redirect:/clients/edit/" + id;
    }
    recipientDetailsService.edit(recipientDetailsEdit, id);
    return "redirect:/clients";
  }
  
  @ModelAttribute("recipientDetails")
  public RecipientDetailsAddDto recipientDetails() {
    return new RecipientDetailsAddDto();
  }
}

