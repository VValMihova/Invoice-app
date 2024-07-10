package bg.softuni.invoice_app.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(
            authorizeRequest ->
                authorizeRequest
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/", "/users/login", "/users/register",
                        "/error", "/users/login?error=true").permitAll()
                    .anyRequest().authenticated())
        .formLogin(
            formLogin ->
                formLogin
                    .loginPage("/users/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/users/login?error=true"))
        .logout(
            logout ->
                logout
                    .logoutUrl("/users/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true))
        .build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  
}
