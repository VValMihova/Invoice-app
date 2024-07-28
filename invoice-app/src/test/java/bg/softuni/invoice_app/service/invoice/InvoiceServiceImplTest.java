package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.invoice.*;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.bankAccountPersist.BankAccountPersistService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {
  
  @Mock
  InvoiceRepository mockRepository;
  @Mock
  ModelMapper modelMapper;
  @Mock
  RecipientDetailsService mockRecipientDetailsService;
  @Mock
  BankAccountService mockBankAccountService;
  @Mock
  UserService mockUserService;
  @Mock
  SaleService mockSaleService;
  @Mock
  BankAccountPersistService mockBankAccountPersistService;
  @InjectMocks
  private InvoiceServiceImpl toTest;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  @Test
  void testConvertToEditDto() {
    InvoiceView invoiceView = new InvoiceView();
    invoiceView.setId(1L);
    invoiceView.setInvoiceNumber(123L);
    invoiceView.setIssueDate(LocalDate.now());
    invoiceView.setRecipient(new RecipientDetailsView().setId(1L));
    BankAccountPersist bankAccountPersist = new BankAccountPersist().setIban("DE89370400440532013000");
    invoiceView.setBankAccountPersist(bankAccountPersist);
    InvoiceItemView itemView = new InvoiceItemView()
        .setName("Item1")
        .setQuantity(BigDecimal.valueOf(1))
        .setUnitPrice(BigDecimal.valueOf(100))
        .setTotalPrice(BigDecimal.valueOf(100));
    invoiceView.setItems(List.of(itemView));
    invoiceView.setTotalAmount(BigDecimal.valueOf(100));
    invoiceView.setVat(BigDecimal.valueOf(20));
    invoiceView.setAmountDue(BigDecimal.valueOf(80));
    
    InvoiceEditDto result = toTest.convertToEditDto(invoiceView);
    
    assertEquals(invoiceView.getId(), result.getId());
    assertEquals(invoiceView.getInvoiceNumber(), result.getInvoiceNumber());
    assertEquals(invoiceView.getIssueDate(), result.getIssueDate());
    assertEquals(invoiceView.getRecipient(), result.getRecipient());
    assertEquals(invoiceView.getBankAccountPersist().getIban(), result.getBankAccountIban());
    assertEquals(invoiceView.getItems().size(), result.getItems().size());
    assertEquals(invoiceView.getTotalAmount(), result.getTotalAmount());
    assertEquals(invoiceView.getVat(), result.getVat());
    assertEquals(invoiceView.getAmountDue(), result.getAmountDue());
  }
  
  @Test
  void testCreateInvoiceWithClient() {
    Long clientId = 1L;
    User user = new User().setId(1L);
    CompanyDetails companyDetails = new CompanyDetails().setId(1L);
    RecipientDetails recipient = new RecipientDetails().setId(clientId);
    
    InvoiceCreateDto invoiceCreateDto = new InvoiceCreateDto()
        .setInvoiceNumber(123L)
        .setIssueDate(LocalDate.now())
        .setItems(List.of(new InvoiceItemDto()
            .setName("Item1")
            .setQuantity(BigDecimal.valueOf(1))
            .setUnitPrice(BigDecimal.valueOf(100))
            .setTotalPrice(BigDecimal.valueOf(100))))
        .setTotalAmount(BigDecimal.valueOf(100))
        .setVat(BigDecimal.valueOf(20))
        .setAmountDue(BigDecimal.valueOf(80))
        .setBankAccountIban("DE89370400440532013000");
    
    BankAccountView bankAccountView = new BankAccountView();
    BankAccountPersist bankAccountPersist = new BankAccountPersist();
    
    when(mockUserService.getUser()).thenReturn(user);
    when(mockUserService.getCompanyDetails()).thenReturn(companyDetails);
    when(mockRecipientDetailsService.getById(clientId)).thenReturn(recipient);
    when(mockBankAccountService.getViewByIban(invoiceCreateDto.getBankAccountIban())).thenReturn(bankAccountView);
    when(mockBankAccountPersistService.add(bankAccountView, user)).thenReturn(bankAccountPersist);
    when(modelMapper.map(any(InvoiceItemDto.class), eq(InvoiceItem.class)))
        .thenAnswer(invocation -> {
          InvoiceItemDto dto = invocation.getArgument(0);
          return new InvoiceItem()
              .setName(dto.getName())
              .setQuantity(dto.getQuantity())
              .setUnitPrice(dto.getUnitPrice())
              .setTotalPrice(dto.getTotalPrice());
        });
    
    toTest.createInvoiceWithClient(clientId, invoiceCreateDto);
    
    ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
    verify(mockRepository).save(invoiceCaptor.capture());
    Invoice capturedInvoice = invoiceCaptor.getValue();
    
    assertEquals(invoiceCreateDto.getInvoiceNumber(), capturedInvoice.getInvoiceNumber());
    assertEquals(invoiceCreateDto.getIssueDate(), capturedInvoice.getIssueDate());
    assertEquals(companyDetails, capturedInvoice.getSupplier());
    assertEquals(recipient, capturedInvoice.getRecipient());
    assertEquals(invoiceCreateDto.getItems().size(), capturedInvoice.getItems().size());
    assertEquals(invoiceCreateDto.getTotalAmount(), capturedInvoice.getTotalAmount());
    assertEquals(invoiceCreateDto.getVat(), capturedInvoice.getVat());
    assertEquals(invoiceCreateDto.getAmountDue(), capturedInvoice.getAmountDue());
    assertEquals(bankAccountPersist, capturedInvoice.getBankAccountPersist());
    assertEquals(user, capturedInvoice.getUser());
  }
  
  
  
  @Test
  void testDeleteById_Success() {
    Long invoiceId = 1L;
    Invoice invoice = new Invoice().setId(invoiceId);
    
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
    
    toTest.deleteById(invoiceId);
    
    verify(mockRepository, times(1)).findById(invoiceId);
    verify(mockSaleService, times(1)).deleteAllByInvoiceId(invoiceId);
    verify(mockRepository, times(1)).delete(invoice);
    verifyNoMoreInteractions(mockRepository, mockSaleService);
  }
  
  @Test
  void testDeleteById_NotFound() {
    Long invoiceId = 1L;
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> toTest.deleteById(invoiceId));
    assertEquals("Invoice", exception.getObjectType());
    
    verify(mockRepository, times(1)).findById(invoiceId);
    verify(mockSaleService, never()).deleteAllByInvoiceId(invoiceId);
    verify(mockRepository, never()).delete(any(Invoice.class));
    verifyNoMoreInteractions(mockRepository, mockSaleService);
  }
  
  
  @Test
  void testGetById_Found() {
    Long invoiceId = 1L;
    Invoice invoice = new Invoice()
        .setId(invoiceId)
        .setInvoiceNumber(123L)
        .setUser(new User().setId(1L))
        .setRecipient(new RecipientDetails().setId(1L))
        .setBankAccountPersist(new BankAccountPersist().setId(1L))
        .setItems(List.of(new InvoiceItem().setId(1L).setName("Item1")))
        .setTotalAmount(BigDecimal.valueOf(100))
        .setVat(BigDecimal.valueOf(20))
        .setAmountDue(BigDecimal.valueOf(80))
        .setIssueDate(LocalDate.now())
        .setSupplier(new CompanyDetails().setId(1L));
    
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
    
    InvoiceView result = toTest.getById(invoiceId);
    
    Assertions.assertNotNull(result);
    assertEquals(invoice.getId(), result.getId());
    assertEquals(invoice.getInvoiceNumber(), result.getInvoiceNumber());
    assertEquals(invoice.getRecipient().getId(), result.getRecipient().getId());
    assertEquals(invoice.getBankAccountPersist().getId(), result.getBankAccountPersist().getId());
    assertEquals(invoice.getItems().size(), result.getItems().size());
    assertEquals(invoice.getTotalAmount(), result.getTotalAmount());
    assertEquals(invoice.getVat(), result.getVat());
    assertEquals(invoice.getAmountDue(), result.getAmountDue());
    assertEquals(invoice.getIssueDate(), result.getIssueDate());
    assertEquals(invoice.getSupplier().getId(), result.getSupplier().getId());
    
    verify(mockRepository, times(1)).findById(invoiceId);
  }
  
  
  @Test
  void testGetById_NotFound() {
    Long invoiceId = 1L;
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    assertThrows(NotFoundObjectException.class, () -> toTest.getById(invoiceId));
    verify(mockRepository, times(1)).findById(invoiceId);
  }
  
  @Test
  void testUpdateInvoice() {
    Long invoiceId = 1L;
    User user = new User().setId(1L);
    Invoice invoice = new Invoice().setId(invoiceId);
    InvoiceEditDto invoiceEditDto = new InvoiceEditDto();
    invoiceEditDto.setInvoiceNumber(123L);
    invoiceEditDto.setIssueDate(LocalDate.now());
    invoiceEditDto.setItems(List.of(new InvoiceItemDto().setName("Item1")));
    invoiceEditDto.setTotalAmount(BigDecimal.valueOf(100));
    invoiceEditDto.setVat(BigDecimal.valueOf(20));
    invoiceEditDto.setAmountDue(BigDecimal.valueOf(80));
    invoiceEditDto.setBankAccountIban("DE89370400440532013000");
    
    BankAccountView bankAccountView = new BankAccountView();
    BankAccountPersist bankAccountPersist = new BankAccountPersist();
    
    when(mockUserService.getUser()).thenReturn(user);
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
    when(mockBankAccountService.getViewByIban(invoiceEditDto.getBankAccountIban())).thenReturn(bankAccountView);
    when(mockBankAccountPersistService.add(bankAccountView, user)).thenReturn(bankAccountPersist);
    when(modelMapper.map(any(InvoiceItemDto.class), eq(InvoiceItem.class)))
        .thenAnswer(invocation -> {
          InvoiceItemDto dto = invocation.getArgument(0);
          return new InvoiceItem()
              .setName(dto.getName())
              .setUnitPrice(dto.getUnitPrice())
              .setQuantity(dto.getQuantity())
              .setTotalPrice(dto.getTotalPrice());
        });
    
    toTest.updateInvoice(invoiceId, invoiceEditDto);
    
    verify(mockRepository, times(1)).findById(invoiceId);
    verify(mockRepository, times(1)).save(invoice);
    verify(mockSaleService, times(1)).deleteAllByInvoiceId(invoiceId);
    verify(mockSaleService, times(invoiceEditDto.getItems().size())).save(any(Sale.class));
    
    assertEquals(invoiceEditDto.getInvoiceNumber(), invoice.getInvoiceNumber());
    assertEquals(invoiceEditDto.getIssueDate(), invoice.getIssueDate());
    assertEquals(bankAccountPersist, invoice.getBankAccountPersist());
    assertEquals(invoiceEditDto.getTotalAmount(), invoice.getTotalAmount());
    assertEquals(invoiceEditDto.getVat(), invoice.getVat());
    assertEquals(invoiceEditDto.getAmountDue(), invoice.getAmountDue());
    
    assertEquals(invoiceEditDto.getItems().size(), invoice.getItems().size());
    InvoiceItemDto dtoItem = invoiceEditDto.getItems().getFirst();
    InvoiceItem invoiceItem = invoice.getItems().getFirst();
    assertEquals(dtoItem.getName(), invoiceItem.getName());
    assertEquals(dtoItem.getUnitPrice(), invoiceItem.getUnitPrice());
    assertEquals(dtoItem.getQuantity(), invoiceItem.getQuantity());
    assertEquals(dtoItem.getTotalPrice(), invoiceItem.getTotalPrice());
  }
  
  
  @Test
  void testGetAllInvoices() {
    Long userId = 1L;
    Invoice invoice = new Invoice().setId(1L);
    AllInvoicesView allInvoicesView = new AllInvoicesView();
    
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    
    when(mockUserService.getCurrentUserId()).thenReturn(userId);
    when(mockRepository.findAllByUserId(userId)).thenReturn(invoices);
    when(modelMapper.map(invoice, AllInvoicesView.class)).thenReturn(allInvoicesView);
    
    List<AllInvoicesView> result = toTest.getAllInvoices();
    
    assertEquals(1, result.size());
    assertEquals(allInvoicesView, result.getFirst());
    verify(mockUserService, times(1)).getCurrentUserId();
    verify(mockRepository, times(1)).findAllByUserId(userId);
    verify(modelMapper, times(1)).map(invoice, AllInvoicesView.class);
  }
  
  @Test
  void testCheckInvoiceExists_True() {
    User user = new User().setId(1L);
    Invoice invoice = new Invoice()
        .setInvoiceNumber(1L)
        .setUser(user);
    
    when(mockUserService.getCurrentUserId()).thenReturn(1L);
    when(mockRepository.findByUserIdAndInvoiceNumber(1L, 1L))
        .thenReturn(Optional.of(invoice));
    
    boolean result = toTest.checkInvoiceExists(1L);
    
    assertTrue(result);
    verify(mockRepository, times(1)).findByUserIdAndInvoiceNumber(1L, 1L);
  }
  
  @Test
  void testCheckInvoiceExists_False() {
    when(mockUserService.getCurrentUserId()).thenReturn(1000000L);
    when(mockRepository.findByUserIdAndInvoiceNumber(1000000L, 1L))
        .thenReturn(Optional.empty());
    
    boolean result = toTest.checkInvoiceExists(1L);
    
    assertFalse(result);
    verify(mockRepository, times(1)).findByUserIdAndInvoiceNumber(1000000L, 1L);
  }
  
  @Test
  void testExistsByBankAccount() {
    when(mockRepository.existsByBankAccountPersist(any(BankAccountPersist.class))).thenReturn(true);
    
    boolean result = toTest.existsByBankAccount(new BankAccountPersist());
    assertTrue(result);
  }
  
  @Test
  void testFindByIdOrThrow_Found() {
    Long invoiceId = 1L;
    Invoice invoice = new Invoice()
        .setId(1L)
        .setInvoiceNumber(1L)
        .setUser(new User().setId(1L))
        .setRecipient(new RecipientDetails().setId(1L))
        .setBankAccountPersist(new BankAccountPersist().setId(1L))
        .setItems(List.of(new InvoiceItem().setId(1L)))
        .setTotalAmount(BigDecimal.valueOf(100))
        .setVat(BigDecimal.valueOf(20))
        .setAmountDue(BigDecimal.valueOf(80))
        .setIssueDate(LocalDate.now());
    
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
    
    Invoice result = toTest.findByIdOrThrow(invoiceId);
    
    Assertions.assertEquals(result.getId(), invoice.getId());
    Assertions.assertEquals(result.getInvoiceNumber(), invoice.getInvoiceNumber());
    Assertions.assertEquals(result.getUser(), invoice.getUser());
    Assertions.assertEquals(result.getRecipient(), invoice.getRecipient());
    Assertions.assertEquals(result.getBankAccountPersist(), invoice.getBankAccountPersist());
    Assertions.assertEquals(result.getItems().size(), invoice.getItems().size());
    Assertions.assertEquals(result.getTotalAmount(), invoice.getTotalAmount());
    Assertions.assertEquals(result.getVat(), invoice.getVat());
    Assertions.assertEquals(result.getAmountDue(), invoice.getAmountDue());
    Assertions.assertEquals(result.getIssueDate(), invoice.getIssueDate());
    verify(mockRepository, times(1)).findById(invoiceId);
  }
  
  @Test
  void testFindByIdOrThrow_NotFound() {
    Long invoiceId = 1L;
    
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    assertThrows(NotFoundObjectException.class, () -> toTest.findByIdOrThrow(invoiceId));
    verify(mockRepository, times(1)).findById(invoiceId);
  }
}

