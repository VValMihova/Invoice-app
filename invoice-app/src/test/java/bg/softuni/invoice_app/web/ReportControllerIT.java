package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.service.pdf.PdfGenerationService;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.service.user.UserService;
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
  
  @MockBean
  private UserService userService;
  
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
    criteria.setStartDate(LocalDate.parse(TEST_START_DATE_STRING));
    criteria.setEndDate(LocalDate.parse(TEST_END_DATE_STRING));
    
    List<SaleReportDto> reportData = Collections.singletonList(new SaleReportDto());
    Long currentUserId = 1L;
    Mockito.when(userService.getCurrentUserId()).thenReturn(currentUserId);
    Mockito.when(saleService.generateReport(Mockito.any(ReportCriteria.class), Mockito.eq(currentUserId))).thenReturn(reportData);
    
    mockMvc.perform(post(REPORT_URL)
            .with(user("user").password("password").roles("USER"))
            .flashAttr("reportCriteria", criteria)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name(REPORT_VIEW))
        .andExpect(model().attributeExists("reportData"));
  }
  
  private static final LocalDate TEST_START_DATE = LocalDate.of(2022, 1, 1);
  private static final LocalDate TEST_END_DATE = LocalDate.of(2022, 12, 31);
  
  @Test
  public void shouldDownloadPdfReport() throws Exception {
    List<SaleReportDto> reportData = Collections.singletonList(new SaleReportDto());
    byte[] pdfBytes = new byte[0];
    Long currentUserId = 1L;
    
    Mockito.when(userService.getCurrentUserId()).thenReturn(currentUserId);
    Mockito.when(saleService.generateReport(Mockito.any(ReportCriteria.class), Mockito.eq(currentUserId))).thenReturn(reportData);
    Mockito.when(pdfGenerationService.generateSalesReportPdf(reportData, TEST_START_DATE, TEST_END_DATE)).thenReturn(pdfBytes);
    
    mockMvc.perform(get(PDF_DOWNLOAD_URL)
            .param("startDate", TEST_START_DATE.toString())
            .param("endDate", TEST_END_DATE.toString())
            .with(user("user").password("password").roles("USER"))
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/pdf"))
        .andExpect(header().string("Content-Disposition", "attachment; filename=sales_report.pdf"));
  }
}