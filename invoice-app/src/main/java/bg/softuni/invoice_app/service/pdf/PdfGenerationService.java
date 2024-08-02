package bg.softuni.invoice_app.service.pdf;

import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.PdfGenerationException;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import com.lowagie.text.pdf.BaseFont;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGenerationService {
  private final InvoiceService invoiceService;
  
  private final SpringTemplateEngine templateEngine;
  
  public PdfGenerationService(InvoiceService invoiceService, SpringTemplateEngine templateEngine) {
    this.invoiceService = invoiceService;
    this.templateEngine = templateEngine;
  }
  
  public byte[] generateInvoicePdf(Long invoiceId, HttpServletRequest request, HttpServletResponse response) {
    InvoiceView invoiceView = invoiceService.getById(invoiceId);
    
    Context context = new Context();
    context.setVariable("invoice", invoiceView);
    
    String htmlContent = templateEngine.process("invoice-pdf", context);
    
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      ITextRenderer renderer = new ITextRenderer();
      renderer.setDocumentFromString(htmlContent);
      renderer.getFontResolver()
          .addFont("C:/Users/YOGA/repositories/Invoice-app/invoice-app/src/main/resources/fonts/verdana.ttf",
              BaseFont.IDENTITY_H,
              BaseFont.NOT_EMBEDDED
          );
      renderer.layout();
      renderer.createPDF(byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    } catch (Exception e) {
      throw new PdfGenerationException(ErrorMessages.PDF_GENERATION_FAILED, e);
    }
  }
  
  public byte[] generateSalesReportPdf(List<SaleReportDto> reportData) {
    Context context = new Context();
    context.setLocale(LocaleContextHolder.getLocale());
    context.setVariable("reportData", reportData);
    
    String htmlContent = templateEngine.process("report-pdf", context);
    
    
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      ITextRenderer renderer = new ITextRenderer();
      renderer.setDocumentFromString(htmlContent);
      renderer.getFontResolver()
          .addFont("C:/Users/YOGA/repositories/Invoice-app/invoice-app/src/main/resources/fonts/verdana.ttf",
              BaseFont.IDENTITY_H,
              BaseFont.NOT_EMBEDDED
          );
      renderer.layout();
      renderer.createPDF(byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    } catch (Exception e) {
      throw new PdfGenerationException(ErrorMessages.PDF_GENERATION_FAILED, e);
    }
  }
}
