package bg.softuni.invoice_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
  
  @Bean
  public RestClient restClient(BankAccountsApiConfig config) {
    return RestClient
        .builder()
        .baseUrl(config.getBaseUrl())
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
