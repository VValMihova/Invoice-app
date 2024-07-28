package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {
  
  @Mock
  private SaleRepository mockSaleRepository;
  
  private SaleServiceImpl toTest;
  
  @BeforeEach
  void setUp() {
    toTest = new SaleServiceImpl(mockSaleRepository);
  }
  
  @Test
  void testGenerateReport() {
    ReportCriteria criteria = new ReportCriteria();
    criteria.setStartDate(LocalDate.of(2023, 1, 1));
    criteria.setEndDate(LocalDate.of(2023, 12, 31));
    
    List<SaleReportDto> expectedReport = Arrays.asList(
        new SaleReportDto("Product1", BigDecimal.valueOf(10)),
        new SaleReportDto("Product2",BigDecimal.valueOf(20))
    );
    
    when(mockSaleRepository.findSalesReport(criteria.getStartDate(), criteria.getEndDate())).thenReturn(expectedReport);
    
    List<SaleReportDto> result = toTest.generateReport(criteria);
    
    assertEquals(expectedReport.size(), result.size());
    assertEquals("Product1", result.get(0).getProductName());
    assertEquals(BigDecimal.valueOf(10), result.get(0).getTotalQuantity());
    assertEquals("Product2", result.get(1).getProductName());
    assertEquals(BigDecimal.valueOf(20), result.get(1).getTotalQuantity());
    
    verify(mockSaleRepository).findSalesReport(criteria.getStartDate(), criteria.getEndDate());
  }
}
