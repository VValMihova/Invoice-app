package bg.softuni.invoiceappbankaccounts.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  UserDetails extractUserInfo(String jwtToken);
}