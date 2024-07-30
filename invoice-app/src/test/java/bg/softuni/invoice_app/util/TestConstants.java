package bg.softuni.invoice_app.util;

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
  
  // Test Recipient Details
  public static final String RECIPIENT_NAME = "Test Recipient";
  public static final String RECIPIENT_ADDRESS = "Test Address";
  public static final String RECIPIENT_MANAGER = "Test Manager";
  public static final String RECIPIENT_EIK = "1234567890";
  public static final String RECIPIENT_VAT_NUMBER = "BG1234567890";
  
  // Test IDs
  public static final Long TEST_ID = 1L;
  public static final Long TEST_ID_2 = 2L;
  
  // Test Dates
  public static final String TEST_START_DATE = "2023-01-01";
  public static final String TEST_END_DATE = "2023-12-31";
  
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
}

