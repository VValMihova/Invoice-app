package bg.softuni.invoice_app.service.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.service.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvoiceServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class InvoiceServiceImplTest {
  @MockBean
  private BankAccountService bankAccountService;

  @MockBean
  private InvoiceRepository invoiceRepository;

  @Autowired
  private InvoiceServiceImpl invoiceServiceImpl;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private RecipientDetailsService recipientDetailsService;

  @MockBean
  private SaleService saleService;

  @MockBean
  private UserService userService;

  @Test
  void testGetAllInvoices() {
    // Arrange
    when(invoiceRepository.findAllByUserId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    when(userService.getCurrentUserId()).thenReturn(1L);

    // Act
    List<AllInvoicesView> actualAllInvoices = invoiceServiceImpl.getAllInvoices();

    // Assert
    verify(invoiceRepository).findAllByUserId(eq(1L));
    verify(userService).getCurrentUserId();
    assertTrue(actualAllInvoices.isEmpty());
  }

  @Test
  void testGetAllInvoices2() {
    // Arrange
    when(userService.getCurrentUserId()).thenThrow(new NotFoundObjectException("Object Type"));

    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> invoiceServiceImpl.getAllInvoices());
    verify(userService).getCurrentUserId();
  }

  @Test
  void testGetAllInvoices3() {
    // Arrange
    Invoice invoice = getInvoice();

    ArrayList<Invoice> invoiceList = new ArrayList<>();
    invoiceList.add(invoice);
    when(invoiceRepository.findAllByUserId(Mockito.<Long>any())).thenReturn(invoiceList);

    AllInvoicesView allInvoicesView = getAllInvoicesView();
    when(modelMapper.map(Mockito.any(), Mockito.<Class<AllInvoicesView>>any())).thenReturn(allInvoicesView);
    when(userService.getCurrentUserId()).thenReturn(1L);

    // Act
    List<AllInvoicesView> actualAllInvoices = invoiceServiceImpl.getAllInvoices();

    // Assert
    verify(invoiceRepository).findAllByUserId(eq(1L));
    verify(userService).getCurrentUserId();
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    assertEquals(1, actualAllInvoices.size());
    assertSame(allInvoicesView, actualAllInvoices.getFirst());
  }

  private static AllInvoicesView getAllInvoicesView() {
    CompanyDetailsEditBindingDto recipient2 = new CompanyDetailsEditBindingDto();
    recipient2.setAddress("42 Main St");
    recipient2.setCompanyName("Company Name");
    recipient2.setEik("Eik");
    recipient2.setId(1L);
    recipient2.setManager("Manager");
    recipient2.setVatNumber("42");

    AllInvoicesView allInvoicesView = new AllInvoicesView();
    allInvoicesView.setAmountDue(new BigDecimal("2.3"));
    allInvoicesView.setId(1L);
    allInvoicesView.setInvoiceNumber(1L);
    allInvoicesView.setIssueDate(LocalDate.of(1970, 1, 1));
    allInvoicesView.setRecipient(recipient2);
    return allInvoicesView;
  }

  private static User getUser() {
    CompanyDetails companyDetails2 = new CompanyDetails();
    companyDetails2.setAddress("42 Main St");
    companyDetails2.setCompanyName("Company Name");
    companyDetails2.setEik("Eik");
    companyDetails2.setId(1L);
    companyDetails2.setManager("Manager");
    companyDetails2.setVatNumber("42");

    User user = new User();
    user.setCompanyDetails(companyDetails2);
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setInvoices(new HashSet<>());
    user.setPassword("iloveyou");
    user.setRecipients(new HashSet<>());
    user.setRoles(new HashSet<>());
    return user;
  }

  @Test
  void testGetAllInvoices4() {
    // Arrange
    Invoice invoice = getInvoice();

    Invoice invoice2 = getInvoice2();

    ArrayList<Invoice> invoiceList = new ArrayList<>();
    invoiceList.add(invoice2);
    invoiceList.add(invoice);
    when(invoiceRepository.findAllByUserId(Mockito.<Long>any())).thenReturn(invoiceList);

    CompanyDetailsEditBindingDto recipient3 = new CompanyDetailsEditBindingDto();
    recipient3.setAddress("42 Main St");
    recipient3.setCompanyName("Company Name");
    recipient3.setEik("Eik");
    recipient3.setId(1L);
    recipient3.setManager("Manager");
    recipient3.setVatNumber("42");

    AllInvoicesView allInvoicesView = new AllInvoicesView();
    allInvoicesView.setAmountDue(new BigDecimal("2.3"));
    allInvoicesView.setId(1L);
    allInvoicesView.setInvoiceNumber(1L);
    LocalDate issueDate = LocalDate.of(1970, 1, 1);
    allInvoicesView.setIssueDate(issueDate);
    allInvoicesView.setRecipient(recipient3);
    when(modelMapper.map(Mockito.any(), Mockito.<Class<AllInvoicesView>>any())).thenReturn(allInvoicesView);
    when(userService.getCurrentUserId()).thenReturn(1L);

    // Act
    List<AllInvoicesView> actualAllInvoices = invoiceServiceImpl.getAllInvoices();

    // Assert
    verify(invoiceRepository).findAllByUserId(eq(1L));
    verify(userService).getCurrentUserId();
    verify(modelMapper, atLeast(1)).map(Mockito.any(), isA(Class.class));
    assertEquals(2, actualAllInvoices.size());
    AllInvoicesView getResult = actualAllInvoices.get(1);
    LocalDate issueDate2 = getResult.getIssueDate();
    assertEquals("1970-01-01", issueDate2.toString());
    CompanyDetailsEditBindingDto recipient4 = getResult.getRecipient();
    assertEquals("42 Main St", recipient4.getAddress());
    assertEquals("42", recipient4.getVatNumber());
    assertEquals("Company Name", recipient4.getCompanyName());
    assertEquals("Eik", recipient4.getEik());
    assertEquals("Manager", recipient4.getManager());
    assertEquals(1L, recipient4.getId().longValue());
    assertEquals(1L, getResult.getId().longValue());
    assertEquals(1L, getResult.getInvoiceNumber().longValue());
    BigDecimal expectedAmountDue = new BigDecimal("2.3");
    assertEquals(expectedAmountDue, getResult.getAmountDue());
    assertSame(allInvoicesView, actualAllInvoices.getFirst());
    assertSame(issueDate, issueDate2);
  }

  private static Invoice getInvoice2() {
    CompanyDetails companyDetails4 = new CompanyDetails();
    companyDetails4.setAddress("17 High St");
    companyDetails4.setCompanyName("42");
    companyDetails4.setEik("42");
    companyDetails4.setId(2L);
    companyDetails4.setManager("42");
    companyDetails4.setVatNumber("Vat Number");

    BankAccount bankAccount2 = new BankAccount();
    bankAccount2.setBic("42");
    bankAccount2.setCompanyDetails(companyDetails4);
    bankAccount2.setCurrency("USD");
    bankAccount2.setIban("42");
    bankAccount2.setId(2L);

    User user3 = getUser3();

    RecipientDetails recipient2 = new RecipientDetails();
    recipient2.setAddress("17 High St");
    recipient2.setCompanyName("42");
    recipient2.setEik("42");
    recipient2.setId(2L);
    recipient2.setManager("42");
    recipient2.setUser(user3);
    recipient2.setVatNumber("Vat Number");

    CompanyDetails supplier2 = new CompanyDetails();
    supplier2.setAddress("17 High St");
    supplier2.setCompanyName("42");
    supplier2.setEik("42");
    supplier2.setId(2L);
    supplier2.setManager("42");
    supplier2.setVatNumber("Vat Number");

    User user4 = getUser3();

    Invoice invoice2 = new Invoice();
    invoice2.setAmountDue(new BigDecimal("2.3"));
    invoice2.setBankAccount(bankAccount2);
    invoice2.setId(2L);
    invoice2.setInvoiceNumber(0L);
    invoice2.setIssueDate(LocalDate.of(1970, 1, 1));
    invoice2.setItems(new ArrayList<>());
    invoice2.setRecipient(recipient2);
    invoice2.setSupplier(supplier2);
    invoice2.setTotalAmount(new BigDecimal("2.3"));
    invoice2.setUser(user4);
    invoice2.setVat(new BigDecimal("2.3"));
    return invoice2;
  }

  private static User getUser3() {
    CompanyDetails companyDetails5 = new CompanyDetails();
    companyDetails5.setAddress("17 High St");
    companyDetails5.setCompanyName("42");
    companyDetails5.setEik("42");
    companyDetails5.setId(2L);
    companyDetails5.setManager("42");
    companyDetails5.setVatNumber("Vat Number");

    User user3 = new User();
    user3.setCompanyDetails(companyDetails5);
    user3.setEmail("john.smith@example.org");
    user3.setId(2L);
    user3.setInvoices(new HashSet<>());
    user3.setPassword("Password");
    user3.setRecipients(new HashSet<>());
    user3.setRoles(new HashSet<>());
    return user3;
  }

  private static Invoice getInvoice() {
    BankAccount bankAccount = getBankAccount();

    User user = getUser();

    RecipientDetails recipient = new RecipientDetails();
    recipient.setAddress("42 Main St");
    recipient.setCompanyName("Company Name");
    recipient.setEik("Eik");
    recipient.setId(1L);
    recipient.setManager("Manager");
    recipient.setUser(user);
    recipient.setVatNumber("42");

    return getInvoice(bankAccount, recipient);
  }

  private static Invoice getInvoice(BankAccount bankAccount, RecipientDetails recipient) {
    CompanyDetails supplier = new CompanyDetails();
    supplier.setAddress("42 Main St");
    supplier.setCompanyName("Company Name");
    supplier.setEik("Eik");
    supplier.setId(1L);
    supplier.setManager("Manager");
    supplier.setVatNumber("42");

    User user2 = getUser();

    Invoice invoice = new Invoice();
    invoice.setAmountDue(new BigDecimal("2.3"));
    invoice.setBankAccount(bankAccount);
    invoice.setId(1L);
    invoice.setInvoiceNumber(1L);
    invoice.setIssueDate(LocalDate.of(1970, 1, 1));
    invoice.setItems(new ArrayList<>());
    invoice.setRecipient(recipient);
    invoice.setSupplier(supplier);
    invoice.setTotalAmount(new BigDecimal("2.3"));
    invoice.setUser(user2);
    invoice.setVat(new BigDecimal("2.3"));
    return invoice;
  }

  @Test
  void testUpdateInvoice() {
    // Arrange
    Invoice invoice = getInvoice();
    Optional<Invoice> ofResult = Optional.of(invoice);

    Invoice invoice2 = getInvoice();
    when(invoiceRepository.save(Mockito.any())).thenReturn(invoice2);
    when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    BankAccount bankAccount3 = getBankAccount();
    when(bankAccountService.getByIban(Mockito.any())).thenReturn(bankAccount3);

    CompanyDetails companyDetails8 = new CompanyDetails();
    companyDetails8.setAddress("42 Main St");
    companyDetails8.setCompanyName("Company Name");
    companyDetails8.setEik("Eik");
    companyDetails8.setId(1L);
    companyDetails8.setManager("Manager");
    companyDetails8.setVatNumber("42");

    User user5 = getUser();
    when(userService.getCompanyDetails()).thenReturn(companyDetails8);
    when(userService.getUser()).thenReturn(user5);
    doNothing().when(saleService).deleteAllByInvoiceId(Mockito.<Long>any());

    InvoiceEditDto invoiceData = new InvoiceEditDto();
    invoiceData.setAmountDue(new BigDecimal("2.3"));
    invoiceData.setBankAccount("3");
    invoiceData.setInvoiceNumber(1L);
    invoiceData.setIssueDate(LocalDate.of(1970, 1, 1));
    invoiceData.setItems(new ArrayList<>());
    invoiceData.setRecipient(new RecipientDetailsView());
    invoiceData.setTotalAmount(new BigDecimal("2.3"));
    invoiceData.setVat(new BigDecimal("2.3"));

    // Act
    invoiceServiceImpl.updateInvoice(1L, invoiceData);

    // Assert
    verify(bankAccountService).getByIban(eq("3"));
    verify(saleService).deleteAllByInvoiceId(eq(1L));
    verify(userService).getCompanyDetails();
    verify(userService).getUser();
    verify(invoiceRepository).findById(eq(1L));
    verify(invoiceRepository).save(isA(Invoice.class));
  }

  private static BankAccount getBankAccount() {
    CompanyDetails companyDetails7 = new CompanyDetails();
    companyDetails7.setAddress("42 Main St");
    companyDetails7.setCompanyName("Company Name");
    companyDetails7.setEik("Eik");
    companyDetails7.setId(1L);
    companyDetails7.setManager("Manager");
    companyDetails7.setVatNumber("42");

    BankAccount bankAccount3 = new BankAccount();
    bankAccount3.setBic("Bic");
    bankAccount3.setCompanyDetails(companyDetails7);
    bankAccount3.setCurrency("GBP");
    bankAccount3.setIban("Iban");
    bankAccount3.setId(1L);
    return bankAccount3;
  }

  @Test
  void testUpdateInvoice2() {
    // Arrange
    Invoice invoice = getInvoice();
    Optional<Invoice> ofResult = Optional.of(invoice);

    Invoice invoice2 = getInvoice();
    when(invoiceRepository.save(Mockito.any())).thenReturn(invoice2);
    when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    BankAccount bankAccount3 = getBankAccount();
    when(bankAccountService.getByIban(Mockito.any())).thenReturn(bankAccount3);

    CompanyDetails companyDetails8 = new CompanyDetails();
    companyDetails8.setAddress("42 Main St");
    companyDetails8.setCompanyName("Company Name");
    companyDetails8.setEik("Eik");
    companyDetails8.setId(1L);
    companyDetails8.setManager("Manager");
    companyDetails8.setVatNumber("42");

    User user5 = getUser();
    when(userService.getCompanyDetails()).thenReturn(companyDetails8);
    when(userService.getUser()).thenReturn(user5);
    doThrow(new NotFoundObjectException("Object Type")).when(saleService).deleteAllByInvoiceId(Mockito.<Long>any());

    InvoiceEditDto invoiceData = new InvoiceEditDto();
    invoiceData.setAmountDue(new BigDecimal("2.3"));
    invoiceData.setBankAccount("3");
    invoiceData.setInvoiceNumber(1L);
    invoiceData.setIssueDate(LocalDate.of(1970, 1, 1));
    invoiceData.setItems(new ArrayList<>());
    invoiceData.setRecipient(new RecipientDetailsView());
    invoiceData.setTotalAmount(new BigDecimal("2.3"));
    invoiceData.setVat(new BigDecimal("2.3"));

    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> invoiceServiceImpl.updateInvoice(1L, invoiceData));
    verify(bankAccountService).getByIban(eq("3"));
    verify(saleService).deleteAllByInvoiceId(eq(1L));
    verify(userService).getCompanyDetails();
    verify(userService).getUser();
    verify(invoiceRepository).findById(eq(1L));
    verify(invoiceRepository).save(isA(Invoice.class));
  }

  @Test
  void testCheckInvoiceExists() {
    // Arrange
    Invoice invoice = getInvoice();
    Optional<Invoice> ofResult = Optional.of(invoice);
    when(invoiceRepository.findByUserIdAndInvoiceNumber(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(ofResult);
    when(userService.getCurrentUserId()).thenReturn(1L);

    // Act
    boolean actualCheckInvoiceExistsResult = invoiceServiceImpl.checkInvoiceExists(1L);

    // Assert
    verify(invoiceRepository).findByUserIdAndInvoiceNumber(eq(1L), eq(1L));
    verify(userService).getCurrentUserId();
    assertTrue(actualCheckInvoiceExistsResult);
  }

  @Test
  void testCheckInvoiceExists2() {
    // Arrange
    when(userService.getCurrentUserId()).thenThrow(new NotFoundObjectException("Object Type"));

    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> invoiceServiceImpl.checkInvoiceExists(1L));
    verify(userService).getCurrentUserId();
  }

  @Test
  void testDeleteById() {
    // Arrange
    Invoice invoice = getInvoice();
    Optional<Invoice> ofResult = Optional.of(invoice);
    doNothing().when(invoiceRepository).delete(Mockito.any());
    when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    invoiceServiceImpl.deleteById(1L);

    // Assert that nothing has changed
    verify(invoiceRepository).delete(isA(Invoice.class));
    verify(invoiceRepository).findById(eq(1L));
  }

  @Test
  void testDeleteById2() {
    // Arrange
    Invoice invoice = getInvoice();
    Optional<Invoice> ofResult = Optional.of(invoice);
    doThrow(new NotFoundObjectException("Object Type")).when(invoiceRepository).delete(Mockito.any());
    when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> invoiceServiceImpl.deleteById(1L));
    verify(invoiceRepository).delete(isA(Invoice.class));
    verify(invoiceRepository).findById(eq(1L));
  }

  @Test
  void testGetById() {
    // Arrange
    BankAccount bankAccount = getBankAccount();

    User user = getUser();

    RecipientDetails recipient = new RecipientDetails();
    recipient.setAddress("42 Main St");
    recipient.setCompanyName("Company Name");
    recipient.setEik("Eik");
    recipient.setId(1L);
    recipient.setManager("Manager");
    recipient.setUser(user);
    recipient.setVatNumber("42");

    CompanyDetails supplier = new CompanyDetails();
    supplier.setAddress("42 Main St");
    supplier.setCompanyName("Company Name");
    supplier.setEik("Eik");
    supplier.setId(1L);
    supplier.setManager("Manager");
    supplier.setVatNumber("42");

    User user2 = getUser();

    Invoice invoice = new Invoice();
    BigDecimal amountDue = new BigDecimal("2.3");
    invoice.setAmountDue(amountDue);
    invoice.setBankAccount(bankAccount);
    invoice.setId(1L);
    invoice.setInvoiceNumber(1L);
    LocalDate issueDate = LocalDate.of(1970, 1, 1);
    invoice.setIssueDate(issueDate);
    invoice.setItems(new ArrayList<>());
    invoice.setRecipient(recipient);
    invoice.setSupplier(supplier);
    BigDecimal totalAmount = new BigDecimal("2.3");
    invoice.setTotalAmount(totalAmount);
    invoice.setUser(user2);
    BigDecimal vat = new BigDecimal("2.3");
    invoice.setVat(vat);
    Optional<Invoice> ofResult = Optional.of(invoice);
    when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    InvoiceView actualById = invoiceServiceImpl.getById(1L);

    // Assert
    verify(invoiceRepository).findById(eq(1L));
    LocalDate issueDate2 = actualById.getIssueDate();
    assertEquals("1970-01-01", issueDate2.toString());
    CompanyDetailsView supplier2 = actualById.getSupplier();
    assertEquals("42 Main St", supplier2.getAddress());
    RecipientDetailsView recipient2 = actualById.getRecipient();
    assertEquals("42 Main St", recipient2.getAddress());
    assertEquals("42", supplier2.getVatNumber());
    assertEquals("42", recipient2.getVatNumber());
    BankAccountView bankAccount2 = actualById.getBankAccount();
    assertEquals("Bic", bankAccount2.getBic());
    assertEquals("Company Name", supplier2.getCompanyName());
    assertEquals("Company Name", recipient2.getCompanyName());
    assertEquals("Eik", supplier2.getEik());
    assertEquals("Eik", recipient2.getEik());
    assertEquals("GBP", bankAccount2.getCurrency());
    assertEquals("Iban", bankAccount2.getIban());
    assertEquals("Manager", supplier2.getManager());
    assertEquals("Manager", recipient2.getManager());
    assertEquals(1L, bankAccount2.getId().longValue());
    assertEquals(1L, supplier2.getId().longValue());
    assertEquals(1L, actualById.getId().longValue());
    assertEquals(1L, actualById.getInvoiceNumber().longValue());
    assertEquals(1L, recipient2.getId().longValue());
    assertTrue(actualById.getItems().isEmpty());
    BigDecimal expectedAmountDue = new BigDecimal("2.3");
    BigDecimal amountDue2 = actualById.getAmountDue();
    assertEquals(expectedAmountDue, amountDue2);
    BigDecimal expectedTotalAmount = new BigDecimal("2.3");
    BigDecimal totalAmount2 = actualById.getTotalAmount();
    assertEquals(expectedTotalAmount, totalAmount2);
    BigDecimal expectedVat = new BigDecimal("2.3");
    BigDecimal vat2 = actualById.getVat();
    assertEquals(expectedVat, vat2);
    assertSame(amountDue, amountDue2);
    assertSame(totalAmount, totalAmount2);
    assertSame(vat, vat2);
    assertSame(issueDate, issueDate2);
  }

  @Test
  void testCreateInvoiceWithClient() {
    // Arrange
    Invoice invoice = getInvoice();
    when(invoiceRepository.save(Mockito.any())).thenReturn(invoice);

    User user3 = getUser();

    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user3);
    recipientDetails.setVatNumber("42");
    when(recipientDetailsService.getById(Mockito.<Long>any())).thenReturn(recipientDetails);

    BankAccount bankAccount2 = getBankAccount();
    when(bankAccountService.getByIban(Mockito.any())).thenReturn(bankAccount2);

    CompanyDetails companyDetails6 = new CompanyDetails();
    companyDetails6.setAddress("42 Main St");
    companyDetails6.setCompanyName("Company Name");
    companyDetails6.setEik("Eik");
    companyDetails6.setId(1L);
    companyDetails6.setManager("Manager");
    companyDetails6.setVatNumber("42");

    User user4 = getUser();
    when(userService.getCompanyDetails()).thenReturn(companyDetails6);
    when(userService.getUser()).thenReturn(user4);

    InvoiceCreateDto invoiceData = getInvoiceCreateDto();

    // Act
    invoiceServiceImpl.createInvoiceWithClient(1L, invoiceData);

    // Assert
    verify(bankAccountService).getByIban(eq("3"));
    verify(recipientDetailsService).getById(eq(1L));
    verify(userService).getCompanyDetails();
    verify(userService).getUser();
    verify(invoiceRepository).save(isA(Invoice.class));
  }

  private static InvoiceCreateDto getInvoiceCreateDto() {
    RecipientDetailsAddDto recipientDetails2 = new RecipientDetailsAddDto();
    recipientDetails2.setAddress("42 Main St");
    recipientDetails2.setCompanyName("Company Name");
    recipientDetails2.setEik("Eik");
    recipientDetails2.setManager("Manager");
    recipientDetails2.setVatNumber("42");

    InvoiceCreateDto invoiceData = new InvoiceCreateDto();
    invoiceData.setAmountDue(new BigDecimal("2.3"));
    invoiceData.setBankAccount("3");
    invoiceData.setInvoiceNumber(1L);
    invoiceData.setIssueDate(LocalDate.of(1970, 1, 1));
    invoiceData.setItems(new ArrayList<>());
    invoiceData.setRecipientDetails(recipientDetails2);
    invoiceData.setTotalAmount(new BigDecimal("2.3"));
    invoiceData.setVat(new BigDecimal("2.3"));
    return invoiceData;
  }

  @Test
  void testCreateInvoiceWithClient2() {
    // Arrange
    when(userService.getUser()).thenThrow(new NotFoundObjectException("Object Type"));

    InvoiceCreateDto invoiceData = getInvoiceCreateDto();

    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> invoiceServiceImpl.createInvoiceWithClient(1L, invoiceData));
    verify(userService).getUser();
  }
}
