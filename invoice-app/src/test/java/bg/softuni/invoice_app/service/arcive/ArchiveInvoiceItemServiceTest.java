package bg.softuni.invoice_app.service.arcive;

import bg.softuni.invoice_app.repository.ArchiveInvoiceItemRepository;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static bg.softuni.invoice_app.TestConstants.TEST_ID;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArchiveInvoiceItemServiceTest {
  
  @Mock
  private ArchiveInvoiceItemRepository mockArchiveInvoiceItemRepository;
  
  @InjectMocks
  private ArchiveInvoiceItemService toTest;
  
  
  @Test
  void testDeleteAllByArchiveInvoiceId() {
    Long archiveInvoiceId = TEST_ID;
    
    toTest.deleteAllByArchiveInvoiceId(archiveInvoiceId);
    
    verify(mockArchiveInvoiceItemRepository).deleteAllByArchiveInvoiceId(archiveInvoiceId);
  }
}
