package bg.softuni.invoice_app.model.dto.bankAccount;

import bg.softuni.invoice_app.validation.bankDetails.annotation.ValidBic;
import bg.softuni.invoice_app.validation.bankDetails.annotation.ValidIban;
import bg.softuni.invoice_app.validation.latin.LatinCharacters;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BankAccountCreateBindingDto {
  @ValidIban
  @NotNull(message = "{bank.account.iban.not.null}")
  @Size(min = 15, max = 34, message = "{bank.account.iban.length}")
  private String iban;
  
  @ValidBic
  @NotNull(message = "{bank.account.bic.not.null}")
  @Size(min = 8, max = 11, message = "{bank.account.bic.length}")
  private String bic;
  
  @LatinCharacters
  @NotNull(message = "{bank.account.currency.not.null}")
  @Size(min = 3, max = 3, message = "{bank.account.currency.length}")
  private String currency;
  
  public String getIban() {
    return iban;
  }
  
  public BankAccountCreateBindingDto setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public String getBic() {
    return bic;
  }
  
  public BankAccountCreateBindingDto setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccountCreateBindingDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}
