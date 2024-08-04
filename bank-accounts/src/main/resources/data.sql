DELETE FROM bank_accounts;

-- admin@abv.bg
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (1, 'DEUTDEFF', 'BGN', 'DE89370400440532013000', '91071533-ae2f-4396-8a46-a7cc3e3d86e0');
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (2, 'DEUTDEFF', 'EUR', 'DE44500105175407324931', '91071533-ae2f-4396-8a46-a7cc3e3d86e0');
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (3, 'DEUTDEFF', 'GBP', 'GB82WEST12345698765432', '91071533-ae2f-4396-8a46-a7cc3e3d86e0');

-- test@abv.bg
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (4, 'DEUTDEFF', 'USD', 'BG80BNBG96611020345678', 'b50fb3f6-45da-4679-b8c7-564fc4bc1a03');
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (5, 'DEUTDEFF', 'EUR', 'DE89370400440532013001', 'b50fb3f6-45da-4679-b8c7-564fc4bc1a03');
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (6, 'DEUTDEFF', 'GBP', 'GB29NWBK60161331926819', 'b50fb3f6-45da-4679-b8c7-564fc4bc1a03');

-- test2@abv.bg
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (7, 'DEUTDEFF', 'DEE', 'FR1420041010050500013M02606', 'b50fb3f7-32da-4679-8a46-564fc4bc1a08');
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (8, 'DEUTDEFF', 'BGN', 'BG80BNBG96611020345679', 'b50fb3f7-32da-4679-8a46-564fc4bc1a08');
INSERT INTO bank_accounts (id, bic, currency, iban, user) VALUES (9, 'DEUTDEFF', 'USD', 'DE44500105175407324932', 'b50fb3f7-32da-4679-8a46-564fc4bc1a08');
