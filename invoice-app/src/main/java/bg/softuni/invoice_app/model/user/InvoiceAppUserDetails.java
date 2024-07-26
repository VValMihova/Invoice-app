package bg.softuni.invoice_app.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class InvoiceAppUserDetails extends User {
  
  private final UUID uuid;
  
  public InvoiceAppUserDetails(
      String uuid,
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.uuid = UUID.fromString(uuid);
  }
  
  public UUID getUuid() {
    return uuid;
  }
}
