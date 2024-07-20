package bg.softuni.invoice_app.service.sale;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.softuni.invoice_app.model.dto.ReportCriteria;
import bg.softuni.invoice_app.model.dto.SaleReportDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.invoice.InvoiceService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SaleServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SaleServiceImplTest {
  @MockBean
  private InvoiceService invoiceService;

  @MockBean
  private SaleRepository saleRepository;

  @Autowired
  private SaleServiceImpl saleServiceImpl;

  @Test
  void testSave() {
    // Arrange
    User user = getUser();

    Sale sale = new Sale();
    sale.setId(1L);
    sale.setInvoiceId(1L);
    sale.setProductName("Product Name");
    sale.setQuantity(new BigDecimal("2.3"));
    sale.setSaleDate(LocalDate.of(1970, 1, 1));
    sale.setUser(user);
    when(saleRepository.save(Mockito.<Sale>any())).thenReturn(sale);

    User user2 = getUser();

    Sale sale2 = new Sale();
    sale2.setId(1L);
    sale2.setInvoiceId(1L);
    sale2.setProductName("Product Name");
    sale2.setQuantity(new BigDecimal("2.3"));
    sale2.setSaleDate(LocalDate.of(1970, 1, 1));
    sale2.setUser(user2);

    // Act
    saleServiceImpl.save(sale2);

    // Assert that nothing has changed
    verify(saleRepository).save(isA(Sale.class));
  }

  private static User getUser() {
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");

    User user = new User();
    user.setCompanyDetails(companyDetails);
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setInvoices(new HashSet<>());
    user.setPassword("iloveyou");
    user.setRecipients(new HashSet<>());
    user.setRoles(new HashSet<>());
    return user;
  }

  @Test
  void testDeleteAllByInvoiceId() {
    // Arrange
    doNothing().when(saleRepository).deleteAllByInvoiceId(Mockito.<Long>any());

    InvoiceView invoiceView = getInvoiceView();
    when(invoiceService.getById(Mockito.<Long>any())).thenReturn(invoiceView);

    // Act
    saleServiceImpl.deleteAllByInvoiceId(1L);

    // Assert that nothing has changed
    verify(saleRepository).deleteAllByInvoiceId(eq(1L));
    verify(invoiceService).getById(eq(1L));
  }

  private static InvoiceView getInvoiceView() {
    InvoiceView invoiceView = new InvoiceView();
    invoiceView.setAmountDue(new BigDecimal("2.3"));
    invoiceView.setBankAccount(new BankAccountView());
    invoiceView.setId(1L);
    invoiceView.setInvoiceNumber(1L);
    invoiceView.setIssueDate(LocalDate.of(1970, 1, 1));
    invoiceView.setItems(new ArrayList<>());
    invoiceView.setRecipient(new RecipientDetailsView());
    invoiceView.setSupplier(new CompanyDetailsView());
    invoiceView.setTotalAmount(new BigDecimal("2.3"));
    invoiceView.setVat(new BigDecimal("2.3"));
    return invoiceView;
  }

  @Test
  void testGenerateReport() {
    // Arrange
    ArrayList<SaleReportDto> saleReportDtoList = new ArrayList<>();
    when(saleRepository.findSalesReport(Mockito.<LocalDate>any(), Mockito.<LocalDate>any()))
        .thenReturn(saleReportDtoList);

    ReportCriteria criteria = new ReportCriteria();
    criteria.setEndDate(LocalDate.of(1970, 1, 1));
    criteria.setStartDate(LocalDate.of(1970, 1, 1));

    // Act
    List<SaleReportDto> actualGenerateReportResult = saleServiceImpl.generateReport(criteria);

    // Assert
    verify(saleRepository).findSalesReport(isA(LocalDate.class), isA(LocalDate.class));
    assertTrue(actualGenerateReportResult.isEmpty());
    assertSame(saleReportDtoList, actualGenerateReportResult);
  }
}
