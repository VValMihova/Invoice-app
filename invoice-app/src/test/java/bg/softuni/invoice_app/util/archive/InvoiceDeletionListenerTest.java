package bg.softuni.invoice_app.util.archive;

import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.utils.archive.InvoiceDeletedEvent;
import bg.softuni.invoice_app.utils.archive.InvoiceDeletionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceDeletionListenerTest {
  
  @Mock
  private ArchiveInvoiceRepository mockArchiveInvoiceRepository;
  
  @Mock
  private SaleService mockSaleService;
  
  @Mock
  private ArchiveSaleRepository mockArchiveSaleRepository;
  
  @InjectMocks
  private InvoiceDeletionListener toTest;
  
  private Invoice invoice;
  private List<Sale> sales;
  
  @BeforeEach
  void setUp() {
    invoice = new Invoice();
    invoice.setId(TEST_ID);
    invoice.setInvoiceNumber(INVOICE_NUMBER);
    invoice.setIssueDate(TEST_DATE_NOW);
    invoice.setSupplier(new CompanyDetails());
    invoice.setRecipient(new RecipientDetails());
    invoice.setTotalAmount(INVOICE_TOTAL_AMOUNT);
    invoice.setVat(INVOICE_VAT);
    invoice.setAmountDue(INVOICE_AMOUNT_DUE);
    invoice.setBankAccountPersist(new BankAccountPersist());
    invoice.setUser(new User());
    
    InvoiceItem item = new InvoiceItem();
    item.setName(INVOICE_ITEM_1_NAME);
    item.setQuantity(ITEM_QUANTITY);
    item.setUnitPrice(ITEM_UNIT_PRICE);
    item.setTotalPrice(ITEM_TOTAL_PRICE);
    invoice.setItems(List.of(item));
    
    sales = List.of(new Sale());
  }
  
  @Test
  void testOnApplicationEvent_Success() {
    when(mockSaleService.findAllByInvoiceId(invoice.getId())).thenReturn(sales);
    
    InvoiceDeletedEvent event = new InvoiceDeletedEvent(this, invoice);
    toTest.onApplicationEvent(event);
    
    verify(mockArchiveInvoiceRepository).save(any(ArchiveInvoice.class));
    verify(mockSaleService).findAllByInvoiceId(invoice.getId());
    verify(mockArchiveSaleRepository, times(sales.size())).save(any(ArchiveSale.class));
  }
}
