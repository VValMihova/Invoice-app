INSERT INTO `invoice-app`.roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO `invoice-app`.roles (id, name) VALUES (2, 'USER');

INSERT INTO `invoice-app`.companies_details (id, address, company_name, eik, manager, vat_number) VALUES (1, 'AdminTestAddress', 'AdminTestCompany', '1234567891', 'AdminTestManager',  'BG1234567891');
INSERT INTO `invoice-app`.companies_details (id, address, company_name, eik, manager, vat_number) VALUES (2, 'Address2', 'Company2', '1234567892', 'Manager2', 'BG1234567892');
INSERT INTO `invoice-app`.companies_details (id, address, company_name, eik, manager, vat_number) VALUES (3, 'Address2', 'Company2', '1234567893', 'Manager2', 'BG1234567893');


-- PASSWORD IS "admin"
INSERT INTO `invoice-app`.users (id, email, password, company_details_id, uuid) VALUES (1, 'admin@abv.bg', '$2a$10$I54tU8P7K2HPz6IWmUjw.ezRlRW6Es2uzQuBkLBvupYulCSzQViE6', 1, '91071533-ae2f-4396-8a46-a7cc3e3d86e0');
-- PASSWORDS ARE "11111" FOR BOTH USERS
INSERT INTO `invoice-app`.users (id, email, password, company_details_id, uuid) VALUES (2, 'test@abv.bg', '$2a$10$Svc.gJKQJzT/864ShV.aZet5Gz3zdlNvCIPtKaQsiKEJhD/.Sn./q', 2, 'b50fb3f6-45da-4679-b8c7-564fc4bc1a03');
INSERT INTO `invoice-app`.users (id, email, password, company_details_id, uuid) VALUES (3, 'test2@abv.bg', '$2a$10$5QkCMwTxhbY4W6cM9c63TeYEyh.CTZxPRdpxBlKFewq/4XMw9oXuW', 3, 'b50fb3f7-32da-4679-8a46-564fc4bc1a08');

INSERT INTO `invoice-app`.user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO `invoice-app`.user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO `invoice-app`.user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO `invoice-app`.user_roles (user_id, role_id) VALUES (3, 2);

INSERT INTO `invoice-app`.recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (1, 'AdminAddress1', 'AdminRecipient1', '1111111111', 'AdminManager1', 'BG1111111111', 1);
INSERT INTO `invoice-app`.recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (2, 'Address2', 'Recipient2', '2222222222', 'Manager2', 'BG2222222222', 2);
INSERT INTO `invoice-app`.recipient_details (id, address, company_name, eik, manager, vat_number, user_id) VALUES (3, 'Address3', 'Recipient3', '3333333333', 'Manager3', 'BG3333333333', 3);
