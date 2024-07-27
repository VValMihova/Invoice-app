package bg.softuni.invoice_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InvoiceAppApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(InvoiceAppApplication.class, args);
  }
  
}
