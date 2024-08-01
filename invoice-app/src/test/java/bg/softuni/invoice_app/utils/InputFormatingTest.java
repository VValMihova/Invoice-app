package bg.softuni.invoice_app.utils;

import org.junit.jupiter.api.Test;

import static bg.softuni.invoice_app.TestConstants.EXPECTED_FORMATED_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputFormatingTest {
  @Test
  public void shouldTrimAndUppercaseInput() {
    String input = "   hello world   ";
    String actualOutput = InputFormating.format(input);
    assertEquals(EXPECTED_FORMATED_STRING, actualOutput);
  }
  
  @Test
  public void shouldHandleEmptyString() {
    String input = "   ";
    String expectedOutput = "";
    String actualOutput = InputFormating.format(input);
    assertEquals(expectedOutput, actualOutput);
  }
  
  @Test
  public void shouldHandleAlreadyFormattedString() {
    String input = "HELLO WORLD";
    String actualOutput = InputFormating.format(input);
    assertEquals(EXPECTED_FORMATED_STRING, actualOutput);
  }
  
  @Test
  public void shouldHandleMixedCaseString() {
    String input = "HeLLo WoRLD";
    String actualOutput = InputFormating.format(input);
    assertEquals(EXPECTED_FORMATED_STRING, actualOutput);
  }
  
  @Test
  public void shouldHandleStringWithLeadingAndTrailingSpaces() {
    String input = "   HELLO WORLD    ";
    String actualOutput = InputFormating.format(input);
    assertEquals(EXPECTED_FORMATED_STRING, actualOutput);
  }
}
