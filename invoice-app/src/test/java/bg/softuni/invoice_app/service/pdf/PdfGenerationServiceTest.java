package bg.softuni.invoice_app.service.pdf;

import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.PdfGenerationException;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.List;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PdfGenerationServiceTest {
  
  @Mock
  private InvoiceService invoiceService;
  
  @Mock
  private SpringTemplateEngine templateEngine;
  
  private PdfGenerationService pdfGenerationService;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    pdfGenerationService = new PdfGenerationService(invoiceService, templateEngine);
  }
  
  @Test
  void testGenerateInvoicePdf() throws Exception {
    Long invoiceId = TEST_ID;
    InvoiceView invoiceView = new InvoiceView();
    invoiceView.setId(invoiceId);
    
    when(invoiceService.getById(anyLong())).thenReturn(invoiceView);
    when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html></html>");
    
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    
    byte[] pdfBytes = pdfGenerationService.generateInvoicePdf(invoiceId, request, response);
    
    assertNotNull(pdfBytes);
    verify(invoiceService).getById(invoiceId);
    verify(templateEngine).process(anyString(), any(Context.class));
  }
  
  private static final LocalDate TEST_START_DATE = LocalDate.of(2022, 1, 1);
  private static final LocalDate TEST_END_DATE = LocalDate.of(2022, 12, 31);
  
  @Test
  void testGenerateSalesReportPdf() throws Exception {
    List<SaleReportDto> reportData = List.of(new SaleReportDto(), new SaleReportDto());
    
    when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html></html>");
    
    byte[] pdfBytes = pdfGenerationService.generateSalesReportPdf(reportData, TEST_START_DATE, TEST_END_DATE);
    
    assertNotNull(pdfBytes);
    

    verify(templateEngine).process(anyString(), any(Context.class));
    verify(templateEngine).process(eq("report-pdf"), any(Context.class));

    verify(templateEngine).process(eq("report-pdf"), argThat(context -> {
      LocalDate startDate = (LocalDate) context.getVariable("startDate");
      LocalDate endDate = (LocalDate) context.getVariable("endDate");
      return TEST_START_DATE.equals(startDate) && TEST_END_DATE.equals(endDate);
    }));
  }
}