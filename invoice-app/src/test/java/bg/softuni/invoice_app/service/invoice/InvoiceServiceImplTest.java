package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.InvoiceNotFoundException;
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
import bg.softuni.invoice_app.utils.archive.InvoiceDeletedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static bg.softuni.invoice_app.TestConstants.*;
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
  @Mock
  private ApplicationEventPublisher mockEventPublisher;
  @InjectMocks
  private InvoiceServiceImpl toTest;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  void testGetById_NotFound() {
    Long invoiceId = TEST_ID;
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    InvoiceNotFoundException exception = assertThrows(InvoiceNotFoundException.class, () -> toTest.getById(invoiceId));
    assertEquals(ErrorMessages.INVOICE_NOT_FOUND, exception.getMessage());
    
    verify(mockRepository, times(1)).findById(invoiceId);
  }
  @Test
  void testFindByIdOrThrow_NotFound() {
    Long invoiceId = TEST_ID;
    
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    InvoiceNotFoundException exception = assertThrows(InvoiceNotFoundException.class, () -> toTest.findByIdOrThrow(invoiceId));
    assertEquals(ErrorMessages.INVOICE_NOT_FOUND, exception.getMessage());
    
    verify(mockRepository).findById(invoiceId);
  }
  
  
  @Test
  void testConvertToEditDto() {
    InvoiceView invoiceView = new InvoiceView();
    invoiceView.setId(TEST_ID);
    invoiceView.setInvoiceNumber(INVOICE_NUMBER);
    invoiceView.setIssueDate(TEST_DATE_NOW);
    invoiceView.setRecipient(new RecipientDetailsView().setId(TEST_ID));
    
    BankAccountPersist bankAccountPersist = new BankAccountPersist()
        .setIban(IBAN_1);
    invoiceView.setBankAccountPersist(bankAccountPersist);
    
    InvoiceItemView itemView = new InvoiceItemView()
        .setName(INVOICE_ITEM_1_NAME)
        .setQuantity(ITEM_QUANTITY)
        .setUnitPrice(ITEM_UNIT_PRICE)
        .setTotalPrice(ITEM_TOTAL_PRICE);
    invoiceView.setItems(List.of(itemView));
    invoiceView.setTotalAmount(INVOICE_TOTAL_AMOUNT);
    invoiceView.setVat(INVOICE_VAT);
    invoiceView.setAmountDue(INVOICE_AMOUNT_DUE);
    
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
    Long clientId = TEST_ID;
    User user = new User().setId(TEST_ID);
    CompanyDetails companyDetails = new CompanyDetails().setId(TEST_ID);
    RecipientDetails recipient = new RecipientDetails().setId(clientId);
    
    InvoiceCreateDto invoiceCreateDto = new InvoiceCreateDto()
        .setInvoiceNumber(INVOICE_NUMBER)
        .setIssueDate(TEST_DATE_NOW)
        .setItems(List.of(new InvoiceItemDto()
            .setName(INVOICE_ITEM_1_NAME)
            .setQuantity(ITEM_QUANTITY)
            .setUnitPrice(ITEM_UNIT_PRICE)
            .setTotalPrice(ITEM_TOTAL_PRICE)))
        .setTotalAmount(INVOICE_TOTAL_AMOUNT)
        .setVat(INVOICE_VAT)
        .setAmountDue(INVOICE_AMOUNT_DUE)
        .setBankAccountIban(IBAN_1);
    
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
    Long invoiceId = TEST_ID;
    Invoice invoice = new Invoice().setId(invoiceId);
    
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
    
    toTest.deleteById(invoiceId);
    
    verify(mockRepository).findById(invoiceId);
    verify(mockSaleService).deleteAllByInvoiceId(invoiceId);
    verify(mockRepository).delete(invoice);
    verify(mockEventPublisher).publishEvent(any(InvoiceDeletedEvent.class));
    verifyNoMoreInteractions(mockRepository, mockSaleService, mockEventPublisher);
  }
  
  @Test
  void testDeleteById_NotFound() {
    Long invoiceId = TEST_ID;
    when(mockRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    InvoiceNotFoundException exception = assertThrows(InvoiceNotFoundException.class, () -> toTest.deleteById(invoiceId));
    assertEquals(ErrorMessages.INVOICE_NOT_FOUND, exception.getMessage());
    
    verify(mockRepository, times(1)).findById(invoiceId);
    verify(mockSaleService, never()).deleteAllByInvoiceId(invoiceId);
    verify(mockRepository, never()).delete(any(Invoice.class));
    verify(mockEventPublisher, never()).publishEvent(any(InvoiceDeletedEvent.class));
    verifyNoMoreInteractions(mockRepository, mockSaleService, mockEventPublisher);
  }

  
  @Test
  void testGetById_Found() {
    Long invoiceId = TEST_ID;
    Invoice invoice = new Invoice()
        .setId(invoiceId)
        .setInvoiceNumber(INVOICE_NUMBER)
        .setUser(new User().setId(TEST_ID))
        .setRecipient(new RecipientDetails().setId(TEST_ID))
        .setBankAccountPersist(new BankAccountPersist().setId(TEST_ID))
        .setItems(List.of(new InvoiceItem().setId(TEST_ID).setName(INVOICE_ITEM_1_NAME)))
        .setTotalAmount(INVOICE_TOTAL_AMOUNT)
        .setVat(INVOICE_VAT)
        .setAmountDue(INVOICE_AMOUNT_DUE)
        .setIssueDate(TEST_DATE_NOW)
        .setSupplier(new CompanyDetails().setId(TEST_ID));
    
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
  void testUpdateInvoice() {
    Long invoiceId = TEST_ID;
    User user = new User().setId(TEST_ID);
    Invoice invoice = new Invoice().setId(invoiceId);
    InvoiceEditDto invoiceEditDto = new InvoiceEditDto();
    invoiceEditDto.setInvoiceNumber(INVOICE_NUMBER);
    invoiceEditDto.setIssueDate(TEST_DATE_NOW);
    invoiceEditDto.setItems(List.of(new InvoiceItemDto().setName(INVOICE_ITEM_1_NAME)));
    invoiceEditDto.setTotalAmount(INVOICE_TOTAL_AMOUNT);
    invoiceEditDto.setVat(INVOICE_VAT);
    invoiceEditDto.setAmountDue(INVOICE_AMOUNT_DUE);
    invoiceEditDto.setBankAccountIban(IBAN_1);
    
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
    
    verify(mockRepository).findById(invoiceId);
    verify(mockRepository).save(invoice);
    verify(mockSaleService).deleteAllByInvoiceId(invoiceId);
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
    Long userId = TEST_ID;
    Invoice invoice = new Invoice().setId(TEST_ID);
    AllInvoicesView allInvoicesView = new AllInvoicesView();
    
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    
    when(mockUserService.getCurrentUserId()).thenReturn(userId);
    when(mockRepository.findAllByUserIdOrderByInvoiceNumber(userId)).thenReturn(invoices);
    when(modelMapper.map(invoice, AllInvoicesView.class)).thenReturn(allInvoicesView);
    
    List<AllInvoicesView> result = toTest.getAllInvoices();
    
    assertEquals(1, result.size());
    assertEquals(allInvoicesView, result.getFirst());
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findAllByUserIdOrderByInvoiceNumber(userId);
    verify(modelMapper).map(invoice, AllInvoicesView.class);
  }
  
  @Test
  void testCheckInvoiceExists_True() {
    User user = new User().setId(TEST_ID);
    Invoice invoice = new Invoice()
        .setInvoiceNumber(TEST_ID)
        .setUser(user);
    
    when(mockUserService.getCurrentUserId()).thenReturn(TEST_ID);
    when(mockRepository.findByUserIdAndInvoiceNumber(TEST_ID, TEST_ID))
        .thenReturn(Optional.of(invoice));
    
    boolean result = toTest.checkInvoiceExists(TEST_ID);
    
    assertTrue(result);
    verify(mockRepository).findByUserIdAndInvoiceNumber(TEST_ID, TEST_ID);
  }
  
  @Test
  void testCheckInvoiceExists_False() {
    when(mockUserService.getCurrentUserId()).thenReturn(NON_EXIST_USER_ID);
    when(mockRepository.findByUserIdAndInvoiceNumber(NON_EXIST_USER_ID, TEST_ID))
        .thenReturn(Optional.empty());
    
    boolean result = toTest.checkInvoiceExists(TEST_ID);
    
    assertFalse(result);
    verify(mockRepository).findByUserIdAndInvoiceNumber(NON_EXIST_USER_ID, TEST_ID);
  }
  
  @Test
  void testExistsByBankAccount() {
    when(mockRepository.existsByBankAccountPersist(any(BankAccountPersist.class))).thenReturn(true);
    
    boolean result = toTest.existsByBankAccount(new BankAccountPersist());
    assertTrue(result);
  }
  
  @Test
  void testFindByIdOrThrow_Found() {
    Long invoiceId = TEST_ID;
    Invoice invoice = new Invoice()
        .setId(TEST_ID)
        .setInvoiceNumber(TEST_ID)
        .setUser(new User().setId(TEST_ID))
        .setRecipient(new RecipientDetails().setId(TEST_ID))
        .setBankAccountPersist(new BankAccountPersist().setId(TEST_ID))
        .setItems(List.of(new InvoiceItem().setId(TEST_ID)))
        .setTotalAmount(INVOICE_TOTAL_AMOUNT)
        .setVat(INVOICE_VAT)
        .setAmountDue(INVOICE_AMOUNT_DUE)
        .setIssueDate(TEST_DATE_NOW);
    
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
    verify(mockRepository).findById(invoiceId);
  }
  
}

