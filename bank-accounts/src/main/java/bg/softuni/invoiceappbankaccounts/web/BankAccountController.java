package bg.softuni.invoiceappbankaccounts.web;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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
  
  @GetMapping("/user/{uuid}")
  public ResponseEntity<List<BankAccountView>>
  getUserAccounts(@PathVariable String uuid) {
    return ResponseEntity.ok(bankAccountService.findByUuid(uuid));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<BankAccountView> deleteBankAccount(@PathVariable Long id) {
    bankAccountService.deleteBankAccount(id);
    return ResponseEntity.noContent().build();
  }

//  // todo make it for current user only
//  @GetMapping
//  public ResponseEntity<Set<BankAccountView>> getAllBankAccounts() {
//    return ResponseEntity.ok(
//        bankAccountService.findAllForCompany()
//    );
//  }

//  @PostMapping
//  public ResponseEntity<BankAccountView> createBankAccount(
//      @RequestBody BankAccountCreateBindingDto bankAccountCreate) {
////    bankAccountService.addBankAccount(bankAccountCreate);
////    return ResponseEntity.ok().build();
//    BankAccountView bankAccountView = bankAccountService.addBankAccount(bankAccountCreate);
//    return ResponseEntity.
//        created(
//            ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(bankAccountView.getId())
//                .toUri()
//        ).body(bankAccountView);
//  }
  
  @PostMapping("/user/{uuid}")
  public ResponseEntity<BankAccountView> createBankAccount(
      @RequestBody BankAccountCreateBindingDto bankAccountCreate,
      @PathVariable String uuid) {
    BankAccountView bankAccountView = bankAccountService.addBankAccountUser(bankAccountCreate, uuid);
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
  public ResponseEntity<BankAccountView> updateBankAccount(
      @PathVariable Long id,
      @RequestBody BankAccountEditBindingDto bankAccountEditBindingDto) {
    BankAccountView updatedBankAccount =
        bankAccountService.updateBankAccount(id, bankAccountEditBindingDto);
    return ResponseEntity.ok(updatedBankAccount);
  }
}
