package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
  
  private final UserService userService;
  private final ArchiveInvoiceService archiveInvoiceService;
  
  public AdminController(UserService userService, ArchiveInvoiceService archiveInvoiceService) {
    this.userService = userService;
    this.archiveInvoiceService = archiveInvoiceService;
  }
  
  @Secured("ROLE_ADMIN")
  @GetMapping
  public String adminPanel(
      Model model,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false) String companyName,
      @RequestParam(required = false) String eik) {
    Page<User> users = userService.findAllExceptCurrent(PageRequest.of(page, 10), companyName, eik);
    List<Boolean> isAdminList = users.stream()
        .map(userService::isUserAdmin)
        .collect(Collectors.toList());
    model.addAttribute("users", users);
    model.addAttribute("isAdminList", isAdminList);
    model.addAttribute("companyName", companyName);
    model.addAttribute("eik", eik);
    return "admin-panel";
  }
  
  @Secured("ROLE_ADMIN")
  @GetMapping("/deleted-invoices")
  public String viewDeletedInvoices(Model model, @RequestParam Long userId, @RequestParam(defaultValue = "0") int page) {
    Page<ArchiveInvoice> deletedInvoices = archiveInvoiceService.findAllByUserId(userId, PageRequest.of(page, 10));
    User user = userService.findById(userId);
    model.addAttribute("deletedInvoices", deletedInvoices);
    model.addAttribute("companyName", user.getCompanyDetails().getCompanyName());
    model.addAttribute("userId", userId);
    return "admin-deleted-invoices";
  }
  
  @Secured("ROLE_ADMIN")
  @PostMapping("/restore-invoice/{invoiceId}")
  public String restoreInvoice(@PathVariable Long invoiceId, @RequestParam Long userId) {
    archiveInvoiceService.restoreInvoice(invoiceId, userId);
    return "redirect:/admin/deleted-invoices?userId=" + userId;
  }
  
  @Secured("ROLE_ADMIN")
  @PostMapping("/make-admin")
  public String makeAdmin(@RequestParam("userId") Long userId) {
    userService.addAdminRoleToUser(userId);
    return "redirect:/admin";
  }
  
  @Secured("ROLE_ADMIN")
  @PostMapping("/remove-admin")
  public String removeAdmin(@RequestParam("userId") Long userId) {
    userService.removeAdminRoleFromUser(userId);
    return "redirect:/admin";
  }
}
