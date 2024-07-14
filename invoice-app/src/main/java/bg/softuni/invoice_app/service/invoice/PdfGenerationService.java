package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.exeption.PdfGenerationException;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerationService {
  private final InvoicesService invoicesService;
  
  private final SpringTemplateEngine templateEngine;
  
  public PdfGenerationService( InvoicesService invoicesService, SpringTemplateEngine templateEngine) {
    this.invoicesService = invoicesService;
    this.templateEngine = templateEngine;
  }
  
  public byte[] generatePdf(Long invoiceId, HttpServletRequest request, HttpServletResponse response) {
    InvoiceView invoiceView = invoicesService.getById(invoiceId);
    
    Context context = new Context();
    context.setVariable("invoice", invoiceView);
    
    String htmlContent = templateEngine.process("invoice-pdf", context);
    
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      ITextRenderer renderer = new ITextRenderer();
      renderer.setDocumentFromString(htmlContent);
      renderer.layout();
      renderer.createPDF(byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    } catch (Exception e) {
      throw new PdfGenerationException("Pdf");
    }
  }
}
