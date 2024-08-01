package bg.softuni.invoice_app.validation.vatMatchesEik;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class VatEikValidatorTest {
  
  private VatEikValidator toTest;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @Mock
  private HibernateConstraintValidatorContext hibernateContext;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    toTest = new VatEikValidator();
    toTest.initialize(new ValidVatEik() {
      @Override
      public Class<? extends java.lang.annotation.Annotation> annotationType() {
        return ValidVatEik.class;
      }
      
      @Override
      public String message() {
        return "VAT number does not match EIK";
      }
      
      @Override
      public Class<?>[] groups() {
        return new Class[0];
      }
      
      @Override
      public Class<? extends jakarta.validation.Payload>[] payload() {
        return new Class[0];
      }
      
      @Override
      public String vatNumber() {
        return "vatNumber";
      }
      
      @Override
      public String eik() {
        return "eik";
      }
    });
    
    when(context.unwrap(HibernateConstraintValidatorContext.class)).thenReturn(hibernateContext);
  }
  
  @Test
  void validVatEikMatch() {
    TestObject testObject = new TestObject("BG12345", "12345");
    assertTrue(toTest.isValid(testObject, context));
  }
  
  private record TestObject(String vatNumber, String eik) {
  }
}