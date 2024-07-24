package bg.softuni.invoice_app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bank-accounts.api")
public class BankAccountsApiConfig {
  
  private String baseUrl;
  
  public String getBaseUrl() {
    return baseUrl;
  }
  
  public BankAccountsApiConfig setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }
}
