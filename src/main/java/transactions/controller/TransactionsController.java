package transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import transactions.assmbler.TransactionsAssembler;
import transactions.exceptions.ParentTransactionDoesNotExistException;
import transactions.model.InstallmentResponse;
import transactions.model.TransactionResponse;
import transactions.service.TransactionsService;

import java.util.List;


@RestController
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final PagedResourcesAssembler<TransactionResponse> pagedResourcesAssembler;
    private final TransactionsAssembler transactionsAssembler;

    @Autowired
    public TransactionsController(TransactionsService transactionsService, PagedResourcesAssembler<TransactionResponse> pagedResourcesAssembler, TransactionsAssembler transactionsAssembler) {
        this.transactionsService = transactionsService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.transactionsAssembler = transactionsAssembler;
    }

    @GetMapping("/transactions")
    public ResponseEntity<PagedModel<EntityModel<TransactionResponse>>> getParentTransactions(Pageable pageable) {
        Page<TransactionResponse> transactions = transactionsService.transactionsFor(pageable);

        return ResponseEntity
                .ok()
                .contentType(MediaTypes.HAL_JSON)
                .body(pagedResourcesAssembler.toModel(transactions, transactionsAssembler));
    }

    @GetMapping("/transactions/{parentId}")
    public ResponseEntity<List<InstallmentResponse>> getInstallmentsForParentTransaction(@PathVariable("parentId") Long parentId) {
        try {
            return ResponseEntity.ok(transactionsService.transactionFor(parentId));
        } catch (ParentTransactionDoesNotExistException e) {
           return ResponseEntity.notFound().build();
        }
    }
}
