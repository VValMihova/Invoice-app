package bg.softuni.invoice_app.service.arcive;

import bg.softuni.invoice_app.exeption.ArchiveInvoiceNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
  
  
  @Test
  void testRestoreInvoice_NotFound() {
    Long invoiceId = 1L;
    when(mockArchiveInvoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    ArchiveInvoiceNotFoundException exception = assertThrows(ArchiveInvoiceNotFoundException.class, () -> toTest.restoreInvoice(invoiceId, 1L));
    assertEquals(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND, exception.getMessage());
    
    verify(mockArchiveInvoiceRepository).findById(invoiceId);
    verifyNoMoreInteractions(mockArchiveInvoiceRepository, mockInvoiceRepository, mockArchiveSaleRepository, mockSaleRepository);
  }
  
  @Test
  void testRestoreInvoice_Found() {
    Long invoiceId = 1L;
    Long userId = 2L;
    Long invoiceNumber = 123L;
    
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setInvoiceNumber(invoiceNumber);
    archiveInvoice.setItems(Collections.singletonList(new ArchiveInvoiceItem()));
    
    when(mockArchiveInvoiceRepository.findById(invoiceId)).thenReturn(Optional.of(archiveInvoice));
    when(mockInvoiceRepository.existsByInvoiceNumber(invoiceNumber)).thenReturn(false);
    
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(invoiceNumber);
    when(mockInvoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
    
    List<ArchiveSale> archiveSales = Collections.singletonList(new ArchiveSale());
    when(mockArchiveSaleRepository.findAllByInvoiceNumberAndUserId(invoiceNumber, userId)).thenReturn(archiveSales);
    
    toTest.restoreInvoice(invoiceId, userId);
    
    verify(mockArchiveInvoiceRepository).findById(invoiceId);
    verify(mockInvoiceRepository).existsByInvoiceNumber(invoiceNumber);
    verify(mockInvoiceRepository).save(any(Invoice.class));
    verify(mockSaleRepository).save(any(Sale.class));
    verify(mockArchiveInvoiceRepository).delete(archiveInvoice);
    verify(mockArchiveSaleRepository).deleteAll(archiveSales);
  }
  
  @Test
  void testFindAllByUserId() {
    Long userId = 1L;
    Pageable pageable = PageRequest.of(0, 10);
    Page<ArchiveInvoice> page = new PageImpl<>(Collections.singletonList(new ArchiveInvoice()));
    
    when(mockArchiveInvoiceRepository.findAllByUserId(userId, pageable)).thenReturn(page);
    
    Page<ArchiveInvoice> result = toTest.findAllByUserId(userId, pageable);
    
    assertEquals(page, result);
    verify(mockArchiveInvoiceRepository).findAllByUserId(userId, pageable);
  }
  
}