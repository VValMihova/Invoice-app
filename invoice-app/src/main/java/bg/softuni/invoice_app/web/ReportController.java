package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.service.pdf.PdfGenerationService;
import bg.softuni.invoice_app.service.sale.SaleService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {
  
  private final SaleService saleService;
  private final PdfGenerationService pdfGenerationService;
  
  public ReportController(SaleService saleService, PdfGenerationService pdfGenerationService) {
    this.saleService = saleService;
    this.pdfGenerationService = pdfGenerationService;
  }
  
  @GetMapping
  public String showSalesReportForm() {
    return "report-create";
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
  
  @GetMapping("/download-pdf")
  public void downloadPdfReport(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                HttpServletResponse response) throws IOException {
    ReportCriteria reportCriteria = new ReportCriteria();
    reportCriteria.setStartDate(startDate);
    reportCriteria.setEndDate(endDate);
    
    List<SaleReportDto> reportData = saleService.generateReport(reportCriteria);
    byte[] pdfBytes = pdfGenerationService.generateSalesReportPdf(reportData);
    
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=sales_report.pdf");
    response.getOutputStream().write(pdfBytes);
    response.getOutputStream().flush();
  }
  
  @ModelAttribute("reportCriteria")
  public ReportCriteria reportCriteria() {
    return new ReportCriteria();
  }
}
