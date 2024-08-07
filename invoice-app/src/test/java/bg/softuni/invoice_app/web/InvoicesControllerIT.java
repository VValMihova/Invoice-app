package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.pdf.PdfGenerationService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoicesControllerIT {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private InvoiceService invoiceService;
  
  @MockBean
  private BankAccountService bankAccountService;
  
  @MockBean
  private UserService userService;
  
  @MockBean
  private PdfGenerationService pdfService;
  
  @MockBean
  private RecipientDetailsService recipientDetailsService;
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldReturnInvoiceCreateWithClientView() throws Exception {
    Long clientId = TEST_ID;
    UUID userUuid = UUID.randomUUID();
    RecipientDetailsView recipientDetailsView = new RecipientDetailsView();
    List<BankAccountView> bankAccounts = List.of(
        new BankAccountView(BIC_CODE, CURRENCY_1, IBAN_1),
        new BankAccountView(BIC_CODE, CURRENCY_2, IBAN_2)
    );
    
    when(recipientDetailsService.findById(clientId)).thenReturn(recipientDetailsView);
    when(userService.getUser()).thenReturn(new User(userUuid));
    when(bankAccountService.getUserAccounts(userUuid.toString())).thenReturn(bankAccounts);
    
    MvcResult result = mockMvc.perform(get(CREATE_INVOICE_WITH_CLIENT_URL + clientId))
        .andExpect(status().isOk())
        .andReturn();
    
    ModelAndView modelAndView = result.getModelAndView();
    assertThat(modelAndView).isNotNull();
    assertThat(modelAndView.getViewName()).isEqualTo(INVOICE_CREATE_WITH_CLIENT_VIEW);
    assertThat(modelAndView.getModel().get("recipient")).isEqualTo(recipientDetailsView);
    assertThat(modelAndView.getModel().get("bankAccounts")).isEqualTo(bankAccounts);
  }
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldDownloadPdfSuccessfully() throws Exception {
    Long invoiceId = TEST_ID;
    byte[] pdfContent = "pdf content".getBytes();
    
    when(pdfService.generateInvoicePdf(eq(invoiceId),
        any(),
        any(HttpServletResponse.class))).thenReturn(pdfContent);
    
    MvcResult result = mockMvc.perform(get(DOWNLOAD_PDF_URL + invoiceId))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/pdf"))
        .andExpect(header().string("Content-Disposition", "attachment; filename=invoice.pdf"))
        .andReturn();
    
    byte[] responseContent = result.getResponse().getContentAsByteArray();
    assertThat(responseContent).isEqualTo(pdfContent);
  }
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldReturnInvoiceEditView() throws Exception {
    Long invoiceId = TEST_ID;
    UUID userUuid = UUID.randomUUID();
    InvoiceView invoiceView = new InvoiceView(
        INVOICE_AMOUNT_DUE,
        new BankAccountPersist(),
        invoiceId,
        INVOICE_NUMBER,
        TEST_DATE_NOW,
        List.of(),
        new RecipientDetailsView(),
        new CompanyDetailsView(),
        INVOICE_TOTAL_AMOUNT,
        INVOICE_VAT
    );
    InvoiceEditDto invoiceEditDto = new InvoiceEditDto();
    invoiceEditDto.setId(invoiceId);
    invoiceEditDto.setInvoiceNumber(invoiceView.getInvoiceNumber());
    invoiceEditDto.setIssueDate(invoiceView.getIssueDate());
    invoiceEditDto.setRecipient(invoiceView.getRecipient());
    invoiceEditDto.setBankAccountIban(invoiceView.getBankAccountPersist().getIban());
    invoiceEditDto.setItems(invoiceView.getItems().stream()
        .map(InvoiceItemDto::new)
        .collect(Collectors.toList()));
    invoiceEditDto.setTotalAmount(invoiceView.getTotalAmount());
    invoiceEditDto.setVat(invoiceView.getVat());
    invoiceEditDto.setAmountDue(invoiceView.getAmountDue());
    
    List<BankAccountView> bankAccounts = List.of(
        new BankAccountView(BIC_CODE, CURRENCY_1, IBAN_1),
        new BankAccountView(BIC_CODE, CURRENCY_2, IBAN_2)
    );
    
    when(invoiceService.getById(invoiceId)).thenReturn(invoiceView);
    when(invoiceService.convertToEditDto(invoiceView)).thenReturn(invoiceEditDto);
    when(userService.getUser()).thenReturn(new User(userUuid));
    when(bankAccountService.getUserAccounts(userUuid.toString())).thenReturn(bankAccounts);
    
    MvcResult result = mockMvc.perform(get(INVOICE_EDIT_URL + invoiceId))
        .andExpect(status().isOk())
        .andReturn();
    
    ModelAndView modelAndView = result.getModelAndView();
    assertThat(modelAndView).isNotNull();
    assertThat(modelAndView.getViewName()).isEqualTo(INVOICE_EDIT_VIEW);
    assertThat(modelAndView.getModel().get("invoiceData")).isEqualTo(invoiceEditDto);
    assertThat(modelAndView.getModel().get("bankAccounts")).isEqualTo(bankAccounts);
  }
  
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldReturnInvoiceView() throws Exception {
    Long invoiceId = TEST_ID;
    InvoiceView invoiceView = new InvoiceView(
        INVOICE_AMOUNT_DUE,
        new BankAccountPersist(),
        invoiceId,
        INVOICE_NUMBER,
        TEST_DATE_NOW,
        List.of(),
        new RecipientDetailsView(),
        new CompanyDetailsView(),
        INVOICE_TOTAL_AMOUNT,
        INVOICE_VAT
    );
    when(invoiceService.getById(invoiceId)).thenReturn(invoiceView);
    
    MvcResult result = mockMvc.perform(get(INVOICE_VIEW_URL + invoiceId))
        .andExpect(status().isOk())
        .andReturn();
    
    ModelAndView modelAndView = result.getModelAndView();
    assertThat(modelAndView).isNotNull();
    assertThat(modelAndView.getViewName()).isEqualTo(INVOICE_VIEW);
    assertThat(modelAndView.getModel().get("invoice")).isEqualTo(invoiceView);
  }
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldReturnInvoicesView() throws Exception {
    List<AllInvoicesView> invoices = List.of(
        new AllInvoicesView(
            TEST_ID,
            INVOICE_NUMBER,
            TEST_DATE_NOW,
            new CompanyDetailsEditBindingDto(),
            INVOICE_TOTAL_AMOUNT
        ),
        new AllInvoicesView(
            TEST_ID_2,
            1002L,
            TEST_DATE_NOW,
            new CompanyDetailsEditBindingDto(),
            INVOICE_TOTAL_AMOUNT
        )
    );
    
    String recipient = "Test Recipient";
    LocalDate issueDate = LocalDate.now();
    
    when(invoiceService.searchInvoices(recipient, issueDate)).thenReturn(invoices);
    
    MvcResult result = mockMvc.perform(get(INVOICES_URL)
            .param("recipient", recipient)
            .param("issueDate", issueDate.toString())
            .with(csrf()))
        .andExpect(status().isOk())
        .andReturn();
    
    ModelAndView modelAndView = result.getModelAndView();
    assertThat(modelAndView).isNotNull();
    assertThat(modelAndView.getViewName()).isEqualTo("invoices");
    assertThat(modelAndView.getModel().get("invoices")).isEqualTo(invoices);
  }
  
}