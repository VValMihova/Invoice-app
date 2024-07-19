package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.ReportCriteria;
import bg.softuni.invoice_app.model.dto.SaleReportDto;
import bg.softuni.invoice_app.service.sale.SaleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {
  
  private final SaleService saleService;
  
  public ReportController(SaleService saleService) {
    this.saleService = saleService;
  }
  
  @GetMapping
  public String showSalesReportForm() {
    return "report";
  }
  
  @PostMapping
  public String generateReport(
      @Valid ReportCriteria reportCriteria,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model) {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("reportCriteria", reportCriteria);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportCriteria", bindingResult);
      return "redirect:/reports";
    }
    
    List<SaleReportDto> reportData = saleService.generateReport(reportCriteria);
    
    model.addAttribute("reportData", reportData);
    return "report-view";
  }
  
  @ModelAttribute("reportCriteria")
  public ReportCriteria reportCriteria() {
    return new ReportCriteria();
  }
}
