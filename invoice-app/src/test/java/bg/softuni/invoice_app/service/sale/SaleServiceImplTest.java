package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
  void testFindAllByInvoiceId() {
    Long invoiceId = TEST_ID;
    List<Sale> expectedSales = Arrays.asList(
        new Sale()
            .setProductName(INVOICE_ITEM_1_NAME)
            .setQuantity(ITEM_QUANTITY)
            .setSaleDate(TEST_DATE_NOW)
            .setInvoiceId(invoiceId),
        new Sale()
            .setProductName(INVOICE_ITEM_2_NAME)
            .setQuantity(ITEM_QUANTITY)
            .setSaleDate(TEST_DATE_NOW)
            .setInvoiceId(invoiceId)
    );
    
    when(mockSaleRepository.findAllByInvoiceId(invoiceId)).thenReturn(expectedSales);
    
    List<Sale> result = toTest.findAllByInvoiceId(invoiceId);
    
    assertEquals(expectedSales.size(), result.size());
    assertEquals(expectedSales.get(0).getProductName(), result.get(0).getProductName());
    assertEquals(expectedSales.get(1).getProductName(), result.get(1).getProductName());
    
    verify(mockSaleRepository).findAllByInvoiceId(invoiceId);
  }
  
  @Test
  void testDeleteAllByInvoiceId() {
    Long invoiceId = TEST_ID;
    
    toTest.deleteAllByInvoiceId(invoiceId);
    
    verify(mockSaleRepository).deleteAllByInvoiceId(invoiceId);
  }
  
  @Test
  void testSave() {
    Sale sale = new Sale();
    sale.setProductName(INVOICE_ITEM_1_NAME);
    sale.setQuantity(ITEM_QUANTITY);
    sale.setSaleDate(TEST_DATE_NOW);
    sale.setInvoiceId(TEST_ID);
    sale.setUser(null);
    
    toTest.save(sale);
    
    verify(mockSaleRepository).save(sale);
  }
  
  
  @Test
  void testGenerateReport() {
    ReportCriteria criteria = new ReportCriteria();
    criteria.setStartDate(LocalDate.parse(TEST_START_DATE));
    criteria.setEndDate(LocalDate.parse(TEST_END_DATE));
    
    List<SaleReportDto> expectedReport = Arrays.asList(
        new SaleReportDto(INVOICE_ITEM_1_NAME, ITEM_QUANTITY),
        new SaleReportDto(INVOICE_ITEM_2_NAME, ITEM_QUANTITY)
    );
    
    when(mockSaleRepository.findSalesReport(criteria.getStartDate(), criteria.getEndDate())).thenReturn(expectedReport);
    
    List<SaleReportDto> result = toTest.generateReport(criteria);
    
    assertEquals(expectedReport.size(), result.size());
    assertEquals(INVOICE_ITEM_1_NAME, result.get(0).getProductName());
    assertEquals(ITEM_QUANTITY, result.get(0).getTotalQuantity());
    assertEquals(INVOICE_ITEM_2_NAME, result.get(1).getProductName());
    assertEquals(ITEM_QUANTITY, result.get(1).getTotalQuantity());
    
    verify(mockSaleRepository).findSalesReport(criteria.getStartDate(), criteria.getEndDate());
  }
}
