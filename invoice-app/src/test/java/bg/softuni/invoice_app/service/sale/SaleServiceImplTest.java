package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {
  
  @Mock
  private SaleRepository mockSaleRepository;
  
  private SaleServiceImpl toTest;
  
  @Mock
  private InvoiceService mockInvoiceService;
  
  @Mock
  private UserService mockUserService;
  
  
  @BeforeEach
  void setUp() {
    toTest = new SaleServiceImpl(mockSaleRepository, mockInvoiceService, mockUserService);
  }
  
  @Test
  void testSave() {
    Sale sale = new Sale();
    toTest.save(sale);
    verify(mockSaleRepository, times(1)).save(sale);
  }
  
  @Test
  void testDeleteAllByInvoiceId() {
    Long invoiceId = TEST_ID;
    Long userId = TEST_ID_2;
    Long invoiceNumber = INVOICE_NUMBER;
    
    InvoiceView invoiceView = new InvoiceView();
    invoiceView.setInvoiceNumber(invoiceNumber);
    
    when(mockInvoiceService.getById(invoiceId)).thenReturn(invoiceView);
    when(mockUserService.getCurrentUserId()).thenReturn(userId);
    
    toTest.deleteAllByInvoiceId(invoiceId);
    
    verify(mockSaleRepository, times(1)).deleteAllByInvoiceNumberAndUserId(invoiceNumber, userId);
  }
  
  @Test
  void testGenerateReport() {
    ReportCriteria criteria = new ReportCriteria();
    criteria.setStartDate(LocalDate.parse(TEST_START_DATE));
    criteria.setEndDate(LocalDate.parse(TEST_END_DATE));
    Long userId = TEST_ID;
    
    List<SaleReportDto> expectedReports = new ArrayList<>();
    when(mockSaleRepository.findSalesReport(criteria.getStartDate(), criteria.getEndDate(), userId))
        .thenReturn(expectedReports);
    
    List<SaleReportDto> actualReports = toTest.generateReport(criteria, userId);
    
    assertEquals(expectedReports, actualReports);
    verify(mockSaleRepository, times(1))
        .findSalesReport(criteria.getStartDate(), criteria.getEndDate(), userId);
  }
  
  @Test
  void testFindAllByInvoiceNumber() {
    Long invoiceNumber = INVOICE_NUMBER;
    Long userId = TEST_ID_2;
    
    List<Sale> expectedSales = new ArrayList<>();
    when(mockSaleRepository.findAllByInvoiceNumberAndUserId(invoiceNumber, userId))
        .thenReturn(expectedSales);
    
    List<Sale> actualSales = toTest.findAllByInvoiceNumber(invoiceNumber, userId);
    
    assertEquals(expectedSales, actualSales);
    verify(mockSaleRepository, times(1))
        .findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
  }
  
}
