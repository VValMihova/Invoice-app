package bg.softuni.invoice_app.config;

import bg.softuni.invoice_app.model.user.InvoiceAppUserDetails;
import bg.softuni.invoice_app.service.JwtService;
import bg.softuni.invoice_app.service.user.AppUserDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestConfig {
  
  @Bean
  public RestClient restClient(
      BankAccountsApiConfig config,
      ClientHttpRequestInterceptor requestInterceptor) {
    return RestClient
        .builder()
        .baseUrl(config.getBaseUrl())
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .requestInterceptor(requestInterceptor)
        .build();
  }
  
  
  @Bean
  public ClientHttpRequestInterceptor requestInterceptor(
      UserService userService,
      JwtService jwtService) {
    return ((request, body, execution) -> {
      userService.getCurrentUser()
          .ifPresent(invoiceAppUserDetails -> {
            String token = jwtService.generateToken(
                    invoiceAppUserDetails.getUuid().toString(),
                    Map.of(
                        "roles",
                        invoiceAppUserDetails.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority)
                            .toList(),
                        "user",
                        invoiceAppUserDetails.getUuid().toString()));
            request.getHeaders().setBearerAuth(token);
          });
      return execution.execute(request, body);
    });
  }
}
