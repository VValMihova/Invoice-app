package bg.softuni.invoice_app.validation.report;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateRangeValidatorTest {
  
  private DateRangeValidator validator;
  private ConstraintValidatorContext context;
  private HibernateConstraintValidatorContext hibernateContext;
  
  @BeforeEach
  public void setUp() {
    validator = new DateRangeValidator();
    context = mock(ConstraintValidatorContext.class);
    hibernateContext = mock(HibernateConstraintValidatorContext.class);
    when(context.unwrap(HibernateConstraintValidatorContext.class)).thenReturn(hibernateContext);
  }
  
  @Test
  public void testValidDateRange() {
    ReportCriteria criteria = new ReportCriteria();
    criteria.setStartDate(LocalDate.of(2023, 1, 1));
    criteria.setEndDate(LocalDate.of(2023, 12, 31));
    
    assertTrue(validator.isValid(criteria, context));
  }
  
}
