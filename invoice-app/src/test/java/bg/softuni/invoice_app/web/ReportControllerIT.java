package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.service.pdf.PdfGenerationService;
import bg.softuni.invoice_app.service.sale.SaleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIT {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private SaleService saleService;
  
  @MockBean
  private PdfGenerationService pdfGenerationService;
  
  @Test
  public void shouldRedirectToFormOnValidationError() throws Exception {
    ReportCriteria criteria = new ReportCriteria();
    
    mockMvc.perform(post(REPORT_URL)
            .with(user("user").password("password").roles("USER"))
            .flashAttr("reportCriteria", criteria)
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REPORT_URL));
  }
  
  @Test
  public void shouldShowSalesReportForm() throws Exception {
    mockMvc.perform(get(REPORT_URL).with(user("user").password("password").roles("USER", "ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name(REPORT_FORM_VIEW));
  }
  
  @Test
  public void shouldGenerateReport() throws Exception {
    ReportCriteria criteria = new ReportCriteria();
    criteria.setStartDate(LocalDate.parse(TEST_START_DATE));
    criteria.setEndDate(LocalDate.parse(TEST_END_DATE));
    
    List<SaleReportDto> reportData = Collections.singletonList(new SaleReportDto());
    Mockito.when(saleService.generateReport(Mockito.any(ReportCriteria.class))).thenReturn(reportData);
    
    mockMvc.perform(post(REPORT_URL)
            .with(user("user").password("password").roles("USER"))
            .flashAttr("reportCriteria", criteria)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name(REPORT_VIEW))
        .andExpect(model().attributeExists("reportData"));
  }
  
  @Test
  public void shouldDownloadPdfReport() throws Exception {
    List<SaleReportDto> reportData = Collections.singletonList(new SaleReportDto());
    byte[] pdfBytes = new byte[0];
    Mockito.when(saleService.generateReport(Mockito.any(ReportCriteria.class))).thenReturn(reportData);
    Mockito.when(pdfGenerationService.generateSalesReportPdf(reportData)).thenReturn(pdfBytes);
    
    mockMvc.perform(get(PDF_DOWNLOAD_URL)
            .param("startDate", TEST_START_DATE)
            .param("endDate", TEST_END_DATE)
            .with(user("user").password("password").roles("USER"))
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/pdf"))
        .andExpect(header().string("Content-Disposition", "attachment; filename=sales_report.pdf"));
  }
}