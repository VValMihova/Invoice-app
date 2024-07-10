package bg.softuni.invoice_app.model.dto.binding;

public class BankAccountDto {
  private Long id;
  private String iban;
  private String bic;
  private String currency;
  
  public String getIban() {
    return iban;
  }
  
  public BankAccountDto setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public String getBic() {
    return bic;
  }
  
  public BankAccountDto setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccountDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public BankAccountDto setId(Long id) {
    this.id = id;
    return this;
  }
}
