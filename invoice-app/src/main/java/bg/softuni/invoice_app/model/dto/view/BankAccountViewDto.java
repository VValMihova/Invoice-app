package bg.softuni.invoice_app.model.dto.view;

public class BankAccountViewDto {
  private Long id;
  private String iban;
  private String bic;
  private String currency;
  
  public String getIban() {
    return iban;
  }
  
  public BankAccountViewDto setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public String getBic() {
    return bic;
  }
  
  public BankAccountViewDto setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccountViewDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public BankAccountViewDto setId(Long id) {
    this.id = id;
    return this;
  }
}
