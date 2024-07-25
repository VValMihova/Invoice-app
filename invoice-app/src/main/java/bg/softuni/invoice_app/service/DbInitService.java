////      todo move to rest
////      BankAccount bankAccount1 = new BankAccount()
////          .setIban("BG80BNBG96611020345678")
////          .setBic("UNCRITMM")
////          .setCurrency("USD")
////          .setCompanyDetails(companyDetails1);
////      bankAccountRepository.save(bankAccount1);
////
////      BankAccountPersist bankAccountPersist1 = modelMapper.map(bankAccount1, BankAccountPersist.class).setUser(user1);
////      bankAccountPersistRepository.save(bankAccountPersist1);
////      todo fix for bank account
////      Invoice invoice1 = new Invoice()
////          .setInvoiceNumber(Long.parseLong("1"))
////          .setIssueDate(LocalDate.parse("2024-07-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
////          .setRecipient(recipientDetails1)
////          .setSupplier(companyDetails1)
////          .setItems(List.of(new InvoiceItem()
////                  .setQuantity(BigDecimal.valueOf(10))
////                  .setName("Product1")
////                  .setUnitPrice(BigDecimal.valueOf(100))
////                  .setTotalPrice(BigDecimal.valueOf(1000.0))))
////          .setBankAccountPersist(bankAccountPersist1)
////          .setUser(user1)
////          .setTotalAmount(BigDecimal.valueOf(1000.0))
////          .setVat(BigDecimal.valueOf(200.0))
////          .setAmountDue(BigDecimal.valueOf(1200.0));
////      invoiceRepository.save(invoice1);
//
//      Sale sale1 = new Sale()
//          .setProductName("Product1")
//          .setQuantity(BigDecimal.valueOf(10))
//          .setSaleDate(LocalDate.parse("2024-07-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//          .setUser(user1)
//          .setInvoiceId(Long.parseLong("1"));
//      saleRepository.save(sale1);
//
//      // todo move to rest
////      BankAccount bankAccount2 = new BankAccount()
////          .setIban("BG80BNBG96611020345677")
////          .setBic("UNCRITMM")
////          .setCurrency("EUR")
////          .setCompanyDetails(companyDetails2);
////      bankAccountRepository.save(bankAccount2);
//    }
//  }
//}
