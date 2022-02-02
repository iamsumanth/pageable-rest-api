import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import transactions.assmbler.TransactionsAssembler;
import transactions.controller.TransactionsController;
import transactions.exceptions.ParentTransactionDoesNotExistException;
import transactions.model.InstallmentResponse;
import transactions.model.TransactionResponse;
import transactions.service.TransactionsService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {TransactionsController.class})
class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @MockBean
    private PagedResourcesAssembler<TransactionResponse> pagedResourcesAssembler;

    @MockBean
    private TransactionsAssembler transactionsAssembler;

    @Test
    public void shouldReturnAllParentTransactions() throws Exception {
        Pageable pageable = Pageable.ofSize(2);
        pageable.withPage(0);

        Page<TransactionResponse> page = Page.empty();
        TransactionResponse transactionResponse = new TransactionResponse(1L, "ABC", "XYZ", 200.0, 100.0);
        page.and(transactionResponse);

        when(transactionsService.transactionsFor(pageable)).thenReturn(page);
        this.mockMvc.perform(get("/transactions?size=2"))
                .andExpect(status().isOk());

        verify(pagedResourcesAssembler).toModel(page, transactionsAssembler);
    }


    @Test
    public void shouldReturnInstallmentDetailsForTransactionId() throws Exception {

        InstallmentResponse installmentResponse = new InstallmentResponse(1L, "XYZ", "ABC", 200.0, 100.0);

        when(transactionsService.transactionFor(1L)).thenReturn(List.of(installmentResponse));
        this.mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenTransactionIdIsInvalid() throws Exception {

        InstallmentResponse installmentResponse = new InstallmentResponse(1L, "XYZ", "ABC", 200.0, 100.0);

        doThrow(ParentTransactionDoesNotExistException.class).when(transactionsService).transactionFor(1L);
        this.mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isNotFound());
    }
}