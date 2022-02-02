package transactions.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import transactions.entity.ChildTransaction;
import transactions.entity.ParentTransaction;
import transactions.exceptions.ParentTransactionDoesNotExistException;
import transactions.model.InstallmentResponse;
import transactions.model.TransactionResponse;
import transactions.repository.ParentTransactionRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TransactionsServiceTest {
    
    private ParentTransactionRepository parentTransactionRepository;
    private TransactionsService transactionsService;

    @BeforeEach
    public void setUp() {
        parentTransactionRepository = mock(ParentTransactionRepository.class);
        transactionsService = new TransactionsService(parentTransactionRepository);
    }
    
    @Test
    public void shouldGetPageableTransactionResponse() {
        Pageable pageable = Pageable.ofSize(1);

        ChildTransaction childTransactionOne = childTransactionFor(1L, 10.0);
        ChildTransaction childTransactionTwo = childTransactionFor(2L, 20.0);
        ChildTransaction childTransactionThree = childTransactionFor(3L, 60.0);
        ParentTransaction parentTransaction = parentTransactionFor(childTransactionOne, childTransactionTwo, childTransactionThree);
        Page<ParentTransaction> parentTransactionPage = new PageImpl<ParentTransaction>(List.of(parentTransaction));

        TransactionResponse transactionResponse = new TransactionResponse(1L, "ABC", "XYZ", 200.0, 90.0);
        Page<TransactionResponse> expectedPageTransactions = new PageImpl<>(List.of(transactionResponse));

        when(parentTransactionRepository.findAll(pageable)).thenReturn(parentTransactionPage);
        Page<TransactionResponse> resultTransactionResponses = transactionsService.transactionsFor(pageable);

        Assertions.assertEquals(expectedPageTransactions, resultTransactionResponses);
    }


    @Test
    public void shouldGetTransactionForParentTransaction() throws ParentTransactionDoesNotExistException {
        long parentTransactionId = 1L;

        ChildTransaction childTransactionOne = childTransactionFor(1L, 10.0);
        ChildTransaction childTransactionTwo = childTransactionFor(2L, 20.0);
        ChildTransaction childTransactionThree = childTransactionFor(3L, 60.0);
        ParentTransaction parentTransaction = parentTransactionFor(childTransactionOne, childTransactionTwo, childTransactionThree);

        InstallmentResponse expectedInstallmentResponseOne = installmentResponseFrom(childTransactionOne, parentTransaction);
        InstallmentResponse expectedInstallmentResponseTwo = installmentResponseFrom(childTransactionTwo, parentTransaction);
        InstallmentResponse expectedInstallmentResponseThree = installmentResponseFrom(childTransactionThree, parentTransaction);
        List<InstallmentResponse> expectedInstallmentResponses = List.of(expectedInstallmentResponseOne, expectedInstallmentResponseTwo, expectedInstallmentResponseThree);

        when(parentTransactionRepository.findById(parentTransactionId)).thenReturn(Optional.of(parentTransaction));
        List<InstallmentResponse> resultInstallmentResponses = transactionsService.transactionFor(parentTransactionId);

        Assertions.assertArrayEquals(expectedInstallmentResponses.toArray(), resultInstallmentResponses.toArray());
    }

    @Test
    public void shouldThrowParentTransactionDoesNotExistExceptionWhenParentTransactionIdIsInvalid() {
        long parentTransactionId = 1L;
        when(parentTransactionRepository.findById(parentTransactionId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ParentTransactionDoesNotExistException.class,
                () -> transactionsService.transactionFor(parentTransactionId));
    }

    private InstallmentResponse installmentResponseFrom(ChildTransaction childTransactionOne, ParentTransaction parentTransaction) {
        return new InstallmentResponse(childTransactionOne.getId(), parentTransaction.getSender(),
                parentTransaction.getReceiver(), parentTransaction.getTotalAmount(), childTransactionOne.getPaidAmount()
        );
    }

    private ParentTransaction parentTransactionFor(ChildTransaction childTransactionOne, ChildTransaction childTransactionTwo, ChildTransaction childTransactionThree) {
        ParentTransaction parentTransaction = new ParentTransaction();
        parentTransaction.setId(1L);
        parentTransaction.setSender("ABC");
        parentTransaction.setReceiver("XYZ");
        parentTransaction.setTotalAmount(200.0);
        parentTransaction.setChildTransaction(List.of(childTransactionOne, childTransactionTwo, childTransactionThree));
        return parentTransaction;
    }

    private ChildTransaction childTransactionFor(long id, double paidAmount) {
        ChildTransaction childTransaction = new ChildTransaction();
        childTransaction.setId(id);
        childTransaction.setPaidAmount(paidAmount);
        return childTransaction;
    }

}