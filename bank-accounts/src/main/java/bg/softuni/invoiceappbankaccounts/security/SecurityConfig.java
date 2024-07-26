package bg.softuni.invoiceappbankaccounts.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  
  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity httpSecurity,
      JwtAuthenticatonFilter jwtAuthenticatonFilter)
      throws Exception {
    
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(autorize -> {
          autorize.requestMatchers(HttpMethod.GET, "/bank-accounts/**").permitAll()
              .anyRequest().authenticated();
        })
//        .authorizeHttpRequests(authorize -> authorize
//            .anyRequest().authenticated())
    .addFilterBefore(jwtAuthenticatonFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
  
  @Bean
  public AuthenticationProvider noopAuthenticationProvider() {
    return new AuthenticationProvider() {
      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
      }
      
      @Override
      public boolean supports(Class<?> authentication) {
        return false;
      }
    };
  }
}