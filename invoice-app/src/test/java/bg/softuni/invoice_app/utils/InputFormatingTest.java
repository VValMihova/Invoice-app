package bg.softuni.invoice_app.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputFormatingTest {

  void testFormat() {
    assertEquals("INPUT", InputFormating.format("Input"));
  }
}
