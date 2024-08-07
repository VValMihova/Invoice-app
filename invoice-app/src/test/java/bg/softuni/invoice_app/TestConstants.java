package bg.softuni.invoice_app;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestConstants {
  
  // URLs for Invoices
  public static final String CREATE_INVOICE_WITH_CLIENT_URL = "/invoices/create-with-client/";
  public static final String INVOICES_URL = "/invoices";
  public static final String INVOICE_VIEW_URL = "/invoices/view/";
  public static final String INVOICE_EDIT_URL = "/invoices/edit/";
  public static final String DOWNLOAD_PDF_URL = "/invoices/download-pdf/";
  
  // Views for Invoices
  public static final String INVOICES_VIEW = "invoices";
  public static final String INVOICE_VIEW = "invoice-view";
  public static final String INVOICE_EDIT_VIEW = "invoice-edit";
  public static final String INVOICE_CREATE_WITH_CLIENT_VIEW = "invoice-create-with-client";
  
  // URLs for Profile
  public static final String PROFILE_URL = "/profile";
  public static final String EDIT_COMPANY_URL = "/profile/edit-company";
  
  // Views for Profile
  public static final String USER_PROFILE_VIEW = "user-profile";
  public static final String COMPANY_EDIT_VIEW = "company-edit";
  
  // URLs for Clients
  public static final String CLIENTS_URL = "/clients";
  public static final String ADD_CLIENT_URL = "/clients/add";
  public static final String EDIT_URL = "/clients/edit/1";
  public static final String EDIT_CLIENT_URL_TEMPLATE = "/clients/edit/{id}";
  public static final String ADD_URL = "/clients/add";
  
  // Views for Clients
  public static final String CLIENTS_PAGE_VIEW = "clients";
  public static final String ADD_CLIENT_FORM_VIEW = "client-add";
  public static final String EDIT_CLIENT_FORM_VIEW = "client-edit";
  public static final String CLIENTS_VIEW = "clients";
  
  // URLs for Reports
  public static final String REPORT_URL = "/reports";
  public static final String PDF_DOWNLOAD_URL = "/reports/download-pdf";
  
  // Views for Reports
  public static final String REPORT_FORM_VIEW = "report-create";
  public static final String REPORT_VIEW = "report-view";
  
  // Test User Credentials
  public static final String TEST_EMAIL = "test@example.com";
  public static final String TEST_EMAIL_2 = "user1@example.com";
  public static final String TEST_EMAIL_3 = "user2@example.com";
  public static final String TEST_PASSWORD = "password";
  public static final String TEST_CONFIRM_PASSWORD = "password";
  
  // Test Company Details
  public static final String COMPANY_NAME = "Test Company";
  public static final String COMPANY_ADDRESS = "Test Address";
  public static final String COMPANY_MANAGER = "Test Manager";
  public static final String COMPANY_EIK = "1234567891";
  public static final String COMPANY_VAT_NUMBER = "BG1234567891";
  public static final String UPDATED_COMPANY_NAME = "Updated Company";
  public static final String UPDATED_COMPANY_ADDRESS = "Updated Address";
  public static final String UPDATED_COMPANY_MANAGER = "Updated Manager";
  public static final String UPDATED_COMPANY_EIK = "0987654321";
  public static final String UPDATED_COMPANY_VAT_NUMBER = "BG0987654321";
  public static final String ANOTHER_COMPANY_NAME = "Another Company";
  public static final String ANOTHER_COMPANY_ADDRESS = "Another Address";
  public static final String ANOTHER_COMPANY_EIK = "0987654321";
  public static final String ANOTHER_COMPANY_VAT = "BG0987654321";
  public static final String ANOTHER_COMPANY_MANAGER = "Another Manager";
  
  // Test Recipient Details
  public static final String RECIPIENT_NAME = "Test Recipient";
  public static final String RECIPIENT_ADDRESS = "Test Address";
  public static final String RECIPIENT_MANAGER = "Test Manager";
  public static final String RECIPIENT_EIK = "1234567890";
  public static final String RECIPIENT_VAT_NUMBER = "BG1234567890";
  
  // Test IDs
  public static final Long TEST_ID = 1L;
  public static final Long TEST_ID_2 = 2L;
  public static final Long INVOICE_NUMBER = 123L;
  public static final Long NON_EXIST_USER_ID = 1000000L;
  public static final String TEST_UUID = "123e4567-e89b-12d3-a456-426614174000";
  
  // Test Dates
  public static final LocalDate TEST_DATE_NOW = LocalDate.now();
  public static final String TEST_START_DATE_STRING = "2023-01-01";
  public static final String TEST_END_DATE_STRING = "2023-12-31";
  
  // Test Bank Account Details
  public static final String BIC_CODE = "UNCRITMM";
  public static final String CURRENCY_1 = "EUR";
  public static final String CURRENCY_2 = "BGN";
  public static final String IBAN_1 = "IT60X0542811101000000123456";
  public static final String IBAN_2 = "BG80BNBG96611020345678";
  
  // URLs and Views for Authentication
  public static final String REGISTER_URL = "/users/register";
  public static final String REDIRECT_REGISTER_URL = "/users/register";
  public static final String REGISTER_VIEW_NAME = "user-register";
  public static final String LOGIN_VIEW_NAME = "user-login";
  public static final String LOGIN_URL = "/users/login";
  public static final String LOGIN_ERROR_PARAM = "error";
  
  //Invoice Items
  public static final String INVOICE_ITEM_1_NAME = "Item 1";
  public static final String INVOICE_ITEM_2_NAME = "Item 2";
  public static final BigDecimal ITEM_QUANTITY = BigDecimal.valueOf(1);
  public static final BigDecimal ITEM_UNIT_PRICE = BigDecimal.valueOf(100);
  public static final BigDecimal ITEM_TOTAL_PRICE = BigDecimal.valueOf(100);
  
  //Invoice
  public static final BigDecimal INVOICE_TOTAL_AMOUNT = BigDecimal.valueOf(100);
  public static final BigDecimal INVOICE_VAT = BigDecimal.valueOf(20);
  public static final BigDecimal INVOICE_AMOUNT_DUE = BigDecimal.valueOf(80);
  
  //Roles
  public static final String ROLE_ADMIN = "ADMIN";
  
  // Formating
  public static final String EXPECTED_FORMATED_STRING = "HELLO WORLD";
  
  //  Attributes
  public static final String ATTRIBUTE_COMPANY_NAME = "companyName";
  public static final String ATTRIBUTE_INVOICES = "invoices";
  public static final String ATTRIBUTE_INVOICE = "invoice";
  public static final String ATTRIBUTE_INVOICE_DATA = "invoiceData";
  public static final String ATTRIBUTE_BANK_ACCOUNTS = "bankAccounts";
  public static final String ATTRIBUTE_RECIPIENT = "recipient";
  public static final String ATTRIBUTE_COMPANY_DETAILS = "companyDetails";
  public static final String ATTRIBUTE_BANK_ACCOUNT_DATA = "bankAccountData";
  public static final String ATTRIBUTE_BANK_ACCOUNT_DATA_EDIT = "bankAccountDataEdit";
  public static final String ATTRIBUTE_BINDING_RESULT = "org.springframework.validation.BindingResult.";
  public static final String ATTRIBUTE_USERS = "users";
  public static final String ATTRIBUTE_DELETED_INVOICES = "deletedInvoices";
  public static final String ATTRIBUTE_USER_ID = "userId";
  
  //  Views
  public static final String VIEW_INDEX = "index";
  public static final String VIEW_INVOICES = "invoices";
  public static final String VIEW_INVOICE_VIEW = "invoice-view";
  public static final String VIEW_INVOICE_EDIT = "invoice-edit";
  public static final String VIEW_INVOICE_CREATE_WITH_CLIENT = "invoice-create-with-client";
  public static final String VIEW_USER_PROFILE = "user-profile";
  public static final String VIEW_COMPANY_EDIT = "company-edit";
  public static final String VIEW_BANK_ACCOUNT_ADD = "bank-account-add";
  public static final String VIEW_BANK_ACCOUNT_EDIT = "bank-account-edit";
  public static final String VIEW_ADMIN = "admin";
  public static final String VIEW_ADMIN_DELETED_INVOICES = "admin-deleted-invoices";
  
  
  // Redirect URLs
  public static final String REDIRECT_PROFILE = "redirect:/profile";
  public static final String REDIRECT_PROFILE_ADD_BANK_ACCOUNT = "redirect:/profile/add-bank-account";
  public static final String PATH_ADMIN = "/admin";
  public static final String PATH_ADMIN_DELETED_INVOICES = "/admin/deleted-invoices";
  public static final String PATH_ADMIN_RESTORE_INVOICE = "/admin/restore-invoice/{invoiceId}";
  public static final String REDIRECT_ADMIN_DELETED_INVOICES = "redirect:/admin/deleted-invoices?userId=";
}

