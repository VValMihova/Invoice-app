package bg.softuni.invoiceappbankaccounts.web;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

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
    return ResponseEntity.ok(bankAccountService.findByIban(iban));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<BankAccountView> deleteBankAccount(@PathVariable Long id) {
    bankAccountService.deleteBankAccount(id);
    return ResponseEntity.noContent().build();
  }
  
  // todo make it for current user only
  @GetMapping
  public ResponseEntity<Set<BankAccountView>> getAllBankAccounts(
      @AuthenticationPrincipal UserDetails userDetails) {
    return ResponseEntity.ok(bankAccountService
        .getAllAccountsPerUser(userDetails.getUsername()));
  }
  
  @PostMapping
  public ResponseEntity<BankAccountView> createBankAccount(
      @RequestBody BankAccountCreateBindingDto bankAccountCreate,
      @AuthenticationPrincipal UserDetails userDetails) {
//    bankAccountService.addBankAccount(bankAccountCreate);
//    return ResponseEntity.ok().build();
    BankAccountView bankAccountView = bankAccountService.addBankAccount(bankAccountCreate, userDetails.getUsername());
    return ResponseEntity.
        created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bankAccountView.getId())
                .toUri()
        ).body(bankAccountView);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<BankAccountView> editBankAccount(@PathVariable Long id, @RequestBody BankAccountEditBindingDto bankAccountEditBindingDto) {
    BankAccountView updatedAccount = bankAccountService.editBankAccount(id, bankAccountEditBindingDto);
    return ResponseEntity.ok(updatedAccount);
  }

  }
