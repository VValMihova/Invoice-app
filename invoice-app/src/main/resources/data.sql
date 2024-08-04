INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');

INSERT INTO companies_details (id, address, company_name, eik, manager, vat_number) VALUES (1, 'AdminTestAddress', 'AdminTestCompany', '1234567891', 'AdminTestManager',  'BG1234567891');
INSERT INTO companies_details (id, address, company_name, eik, manager, vat_number) VALUES (2, 'Address2', 'Company2', '1234567892', 'Manager2', 'BG1234567892');
INSERT INTO companies_details (id, address, company_name, eik, manager, vat_number) VALUES (3, 'Address2', 'Company2', '1234567893', 'Manager2', 'BG1234567893');

-- PASSWORD IS "admin"
INSERT INTO users (id, email, password, company_details_id, uuid) VALUES (1, 'admin@abv.bg', '$2a$10$I54tU8P7K2HPz6IWmUjw.ezRlRW6Es2uzQuBkLBvupYulCSzQViE6', 1, '91071533-ae2f-4396-8a46-a7cc3e3d86e0');
-- PASSWORDS ARE "11111" FOR BOTH USERS
INSERT INTO users (id, email, password, company_details_id, uuid) VALUES (2, 'test@abv.bg', '$2a$10$Svc.gJKQJzT/864ShV.aZet5Gz3zdlNvCIPtKaQsiKEJhD/.Sn./q', 2, 'b50fb3f6-45da-4679-b8c7-564fc4bc1a03');
INSERT INTO users (id, email, password, company_details_id, uuid) VALUES (3, 'test2@abv.bg', '$2a$10$5QkCMwTxhbY4W6cM9c63TeYEyh.CTZxPRdpxBlKFewq/4XMw9oXuW', 3, 'b50fb3f7-32da-4679-8a46-564fc4bc1a08');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);

-- admin@abv.bg
INSERT INTO recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (1, 'AdminAddress1', 'AdminRecipient1', '1111111111', 'AdminManager1', 'BG1111111111', 1);
INSERT INTO recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (4, 'AdminAddress4', 'AdminRecipient4', '4444444444', 'AdminManager4', 'BG4444444444', 1);

-- test@abv.bg
INSERT INTO recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (2, 'Address2', 'Recipient2', '2222222222', 'Manager2', 'BG2222222222', 2);
INSERT INTO recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (5, 'Address5', 'Recipient5', '5555555555', 'Manager5', 'BG5555555555', 2);

-- test2@abv.bg
INSERT INTO recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (3, 'Address3', 'Recipient3', '3333333333', 'Manager3', 'BG3333333333', 3);
INSERT INTO recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (6, 'Address6', 'Recipient6', '6666666666', 'Manager6', 'BG6666666666', 3);

-- admin@abv.bg
INSERT INTO bank_accounts_persistant (id, bic, currency, iban, user_id) VALUES (1, 'DEUTDEFF', 'BGN', 'DE89370400440532013000', 1);
INSERT INTO bank_accounts_persistant (id, bic, currency, iban, user_id) VALUES (2, 'DEUTDEFF', 'EUR', 'DE44500105175407324931', 1);

-- test@abv.bg
INSERT INTO bank_accounts_persistant (id, bic, currency, iban, user_id) VALUES (3, 'DEUTDEFF', 'USD', 'BG80BNBG96611020345678', 2);
INSERT INTO bank_accounts_persistant (id, bic, currency, iban, user_id) VALUES (4, 'DEUTDEFF', 'EUR', 'DE89370400440532013001', 2);

-- test2@abv.bg
INSERT INTO bank_accounts_persistant (id, bic, currency, iban, user_id) VALUES (5, 'DEUTDEFF', 'DEE', 'FR1420041010050500013M02606', 3);
INSERT INTO bank_accounts_persistant (id, bic, currency, iban, user_id) VALUES (6, 'DEUTDEFF', 'BGN', 'BG80BNBG96611020345679', 3);

-- Добавяне на данни в таблицата invoices за admin@abv.bg
INSERT INTO invoices (id, invoice_number, issue_date, total_amount, vat, amount_due, bank_account_persist_id, user_id, recipient_id, supplier_id) VALUES (1, 1, '2023-01-01', 1000.00, 200.00, 1200.00, 1, 1, 1, 1);
INSERT INTO invoices (id, invoice_number, issue_date, total_amount, vat, amount_due, bank_account_persist_id, user_id, recipient_id, supplier_id) VALUES (2, 2, '2023-01-15', 1500.00, 300.00, 1800.00, 2, 1, 4, 1);

-- Добавяне на данни в таблицата invoices за test@abv.bg
INSERT INTO invoices (id, invoice_number, issue_date, total_amount, vat, amount_due, bank_account_persist_id, user_id, recipient_id, supplier_id) VALUES (3, 1, '2023-02-01', 2000.00, 400.00, 2400.00, 3, 2, 2, 2);
INSERT INTO invoices (id, invoice_number, issue_date, total_amount, vat, amount_due, bank_account_persist_id, user_id, recipient_id, supplier_id) VALUES (4, 2, '2023-02-15', 2500.00, 500.00, 3000.00, 4, 2, 5, 2);

-- Добавяне на данни в таблицата invoices за test2@abv.bg
INSERT INTO invoices (id, invoice_number, issue_date, total_amount, vat, amount_due, bank_account_persist_id, user_id, recipient_id, supplier_id) VALUES (5, 1, '2023-03-01', 3000.00, 600.00, 3600.00, 5, 3, 3, 3);
INSERT INTO invoices (id, invoice_number, issue_date, total_amount, vat, amount_due, bank_account_persist_id, user_id, recipient_id, supplier_id) VALUES (6, 2, '2023-03-15', 3500.00, 700.00, 4200.00, 6, 3, 6, 3);

-- admin@abv.bg
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (1, 'Product1', 1, 500.00, 500.00, 1);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (2, 'Product2', 1, 500.00, 500.00, 1);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (3, 'Product3', 1, 750.00, 750.00, 2);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (4, 'Product4', 1, 750.00, 750.00, 2);

-- test@abv.bg
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (5, 'Product5', 1, 1000.00, 1000.00, 3);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (6, 'Product6', 1, 1000.00, 1000.00, 3);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (7, 'Product7', 1, 1250.00, 1250.00, 4);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (8, 'Product8', 1, 1250.00, 1250.00, 4);

-- test2@abv.bg
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (9, 'Product9', 1, 1500.00, 1500.00, 5);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (10, 'Product10', 1, 1500.00, 1500.00, 5);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (11, 'Product11', 1, 1750.00, 1750.00, 6);
INSERT INTO invoice_items (id, name, quantity, unit_price, total_price, invoice_id) VALUES (12, 'Product12', 1, 1750.00, 1750.00, 6);

-- admin@abv.bg
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (1, 'Product1', 1, '2023-01-01', 1, 1);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (2, 'Product2', 1, '2023-01-01', 1, 1);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (3, 'Product3', 1, '2023-01-15', 2, 1);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (4, 'Product4', 1, '2023-01-15', 2, 1);

-- test@abv.bg
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (5, 'Product5', 1, '2023-02-01', 3, 2);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (6, 'Product6', 1, '2023-02-01', 3, 2);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (7, 'Product7', 1, '2023-02-15', 4, 2);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (8, 'Product8', 1, '2023-02-15', 4, 2);

-- test2@abv.bg
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (9, 'Product9', 1, '2023-03-01', 5, 3);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (10, 'Product10', 1, '2023-03-01', 5, 3);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (11, 'Product11', 1, '2023-03-15', 6, 3);
INSERT INTO sales (id, product_name, quantity, sale_date, invoice_id, user_id) VALUES (12, 'Product12', 1, '2023-03-15', 6, 3);