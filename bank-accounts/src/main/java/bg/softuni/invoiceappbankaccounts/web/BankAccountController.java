package bg.softuni.invoiceappbankaccounts.web;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.service.BankAccountService;
import bg.softuni.invoiceappbankaccounts.service.exception.ObjectNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {
  
  private final BankAccountService bankAccountService;
  
  public BankAccountController(BankAccountService bankAccountService) {
    this.bankAccountService = bankAccountService;
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<BankAccountView> getBankAccountById(@PathVariable Long id) {
    return ResponseEntity.ok(bankAccountService.findById(id));
  }
  
  @GetMapping("/iban/{iban}")
  public ResponseEntity<BankAccountView> getBankAccountByIban(@PathVariable String iban) {
    return ResponseEntity.ok(bankAccountService.getBankAccountByIban(iban));
  }
  
  @GetMapping("/user/{uuid}")
  public ResponseEntity<List<BankAccountView>> getUserAccounts(@PathVariable String uuid) {
    return ResponseEntity.ok(bankAccountService.findByUuid(uuid));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
    bankAccountService.deleteBankAccount(id);
    return ResponseEntity.noContent().build();
  }
  
  @PostMapping("/user/{uuid}")
  public ResponseEntity<BankAccountView> createBankAccount(
      @RequestBody BankAccountCreateBindingDto bankAccountCreate,
      @PathVariable String uuid) {
    BankAccountView bankAccountView = bankAccountService.addBankAccountUser(bankAccountCreate, uuid);
    return ResponseEntity.created(
        ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(bankAccountView.getId())
            .toUri()
    ).body(bankAccountView);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<BankAccountView> updateBankAccount(
      @PathVariable Long id,
      @RequestBody BankAccountEditBindingDto bankAccountEditBindingDto) {
    BankAccountView updatedBankAccount;
    try {
      updatedBankAccount = bankAccountService.updateBankAccount(id, bankAccountEditBindingDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null); // Или можете да върнете съобщението за грешка
    }
    return ResponseEntity.ok(updatedBankAccount);
  }
  
  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<Void> handleObjectNotFoundException() {
    return ResponseEntity.notFound().build();
  }
  
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(errors);
  }
}
