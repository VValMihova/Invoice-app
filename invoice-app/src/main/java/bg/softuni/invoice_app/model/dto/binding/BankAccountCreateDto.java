package bg.softuni.invoice_app.model.dto.binding;

import bg.softuni.invoice_app.validation.annotation.UniqueIban;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BankAccountCreateDto {
  @UniqueIban
  @NotNull(message = "{bank.account.iban.not.null}")
  @Size(min = 22, max = 32, message = "{bank.account.iban.length}")
  private String iban;
  
  @NotNull(message = "{bank.account.bic.not.null}")
  @Size(min = 8, max = 11, message = "{bank.account.bic.length}")
  private String bic;
  
  @NotNull(message = "{bank.account.currency.not.null}")
  @Size(min = 3, max = 3, message = "{bank.account.currency.length}")
  private String currency;
  
  public String getIban() {
    return iban;
  }
  
  public BankAccountCreateDto setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public String getBic() {
    return bic;
  }
  
  public BankAccountCreateDto setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccountCreateDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}
