package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class RecipientDetailsController {
  private final RecipientDetailsService recipientDetailsService;
  
  public RecipientDetailsController(
      RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  //  @GetMapping
//  public String clientsPage(Model model) {
//    model.addAttribute("clients", recipientDetailsService.findAll());
//    return "clients";
//  }
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
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("recipientDetails", recipientDetails);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipientDetails", bindingResult);
      return "redirect:/clients/add";
    }
    recipientDetailsService.addRecipientDetails(recipientDetails);
    return "clients";
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

