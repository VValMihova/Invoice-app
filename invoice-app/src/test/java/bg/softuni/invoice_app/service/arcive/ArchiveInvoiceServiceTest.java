package bg.softuni.invoice_app.service.arcive;

import bg.softuni.invoice_app.exeption.ArchiveInvoiceNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArchiveInvoiceServiceTest {
  
  @Mock
  private ArchiveInvoiceRepository mockArchiveInvoiceRepository;
  
  @Mock
  private InvoiceRepository mockInvoiceRepository;
  
  @Mock
  private ArchiveSaleRepository mockArchiveSaleRepository;
  
  @Mock
  private SaleRepository mockSaleRepository;
  
  @InjectMocks
  private ArchiveInvoiceService toTest;
  
  private ArchiveInvoice archiveInvoice;
  private List<ArchiveSale> archiveSales;
  
  @BeforeEach
  void setUp() {
    archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setId(TEST_ID);
    archiveInvoice.setInvoiceNumber(INVOICE_NUMBER);
    archiveInvoice.setIssueDate(TEST_DATE_NOW);
    archiveInvoice.setSupplier(new CompanyDetails());
    archiveInvoice.setRecipient(new RecipientDetails());
    archiveInvoice.setTotalAmount(INVOICE_TOTAL_AMOUNT);
    archiveInvoice.setVat(INVOICE_VAT);
    archiveInvoice.setAmountDue(INVOICE_AMOUNT_DUE);
    archiveInvoice.setBankAccountPersist(new BankAccountPersist());
    archiveInvoice.setUser(new User());
    
    ArchiveInvoiceItem archiveItem = new ArchiveInvoiceItem();
    archiveItem.setName(INVOICE_ITEM_1_NAME);
    archiveItem.setQuantity(ITEM_QUANTITY);
    archiveItem.setUnitPrice(ITEM_UNIT_PRICE);
    archiveItem.setTotalPrice(ITEM_TOTAL_PRICE);
    archiveInvoice.setItems(List.of(archiveItem));
    
    archiveSales = List.of(new ArchiveSale());
  }
  
//  @Test
//  void testRestoreInvoice_Success() {
//    when(mockArchiveInvoiceRepository.findById(archiveInvoice.getId())).thenReturn(Optional.of(archiveInvoice));
//    when(mockInvoiceRepository.existsByInvoiceNumber(archiveInvoice.getInvoiceNumber())).thenReturn(false);
//    when(mockArchiveSaleRepository.findAllByInvoiceId(archiveInvoice.getId())).thenReturn(archiveSales);
//
//    toTest.restoreInvoice(archiveInvoice.getId());
//
//    verify(mockArchiveInvoiceRepository).findById(archiveInvoice.getId());
//    verify(mockInvoiceRepository).existsByInvoiceNumber(archiveInvoice.getInvoiceNumber());
//    verify(mockInvoiceRepository).save(any(Invoice.class));
//    verify(mockSaleRepository, times(archiveSales.size())).save(any(Sale.class));
//    verify(mockArchiveInvoiceRepository).delete(archiveInvoice);
//    verify(mockArchiveSaleRepository).deleteAll(archiveSales);
//  }
  
//  @Test
//  void testRestoreInvoice_InvoiceNotFound() {
//    when(mockArchiveInvoiceRepository.findById(archiveInvoice.getId())).thenReturn(Optional.empty());
//
//    ArchiveInvoiceNotFoundException exception = assertThrows(ArchiveInvoiceNotFoundException.class,
//        () -> toTest.restoreInvoice(archiveInvoice.getId()));
//
//    assertEquals(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND, exception.getMessage());
//
//    verify(mockArchiveInvoiceRepository).findById(archiveInvoice.getId());
//    verifyNoMoreInteractions(mockInvoiceRepository, mockArchiveSaleRepository, mockSaleRepository);
//  }

}