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
