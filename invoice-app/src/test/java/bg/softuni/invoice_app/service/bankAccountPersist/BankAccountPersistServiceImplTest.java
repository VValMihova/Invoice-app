package bg.softuni.invoice_app.service.bankAccountPersist;

import bg.softuni.invoice_app.repository.BankAccountPersistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class BankAccountPersistServiceImplTest {
  private BankAccountPersistServiceImpl toTest;
  
  @Mock
  private BankAccountPersistRepository mockRepository;
  @Mock
  private ModelMapper mockModelMapper;
  
  @BeforeEach
  public void setUp() {
    toTest = new BankAccountPersistServiceImpl(mockRepository, mockModelMapper);
  }
  
}
