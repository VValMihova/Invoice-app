package bg.softuni.invoice_app.model.dto.binding;

public class BankAccountCreateDto {
  private String iban;
  private String bic;
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
