package bg.softuni.invoice_app.model.dto.bankAccount;


public class BankAccountView {
  private Long id;
  private String iban;
  private String bic;
  private String currency;
  
  public BankAccountView() {
  }
  
  
  public String getIban() {
    return iban;
  }
  
  public BankAccountView setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public String getBic() {
    return bic;
  }
  
  public BankAccountView setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccountView setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public BankAccountView setId(Long id) {
    this.id = id;
    return this;
  }
  
}
