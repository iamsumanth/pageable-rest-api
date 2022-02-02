package transactions.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import transactions.entity.ChildTransaction;
import transactions.entity.ParentTransaction;
import transactions.exceptions.ParentTransactionDoesNotExistException;
import transactions.model.InstallmentResponse;
import transactions.model.TransactionResponse;
import transactions.repository.ParentTransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    private final ParentTransactionRepository parentTransactionRepository;

    public TransactionsService(ParentTransactionRepository parentTransactionRepository) {
        this.parentTransactionRepository = parentTransactionRepository;
    }

    public Page<TransactionResponse> transactionsFor(Pageable pageable) {
        Page<ParentTransaction> parentTransactions = parentTransactionRepository.findAll(pageable);

        return parentTransactions.map(parentTransaction -> {
            Double totalInstallmentPaidAmount = totalInstallmentPaid(parentTransaction);
            return buildTransactionResponseFrom(parentTransaction, totalInstallmentPaidAmount);
        });
    }

    public List<InstallmentResponse> transactionFor(Long parentTransactionId) throws ParentTransactionDoesNotExistException {
        Optional<ParentTransaction> parentTransactionOptional = parentTransactionRepository.findById(parentTransactionId);
        if (parentTransactionOptional.isEmpty()) {
            throw new ParentTransactionDoesNotExistException("Invalid parent transaction id");
        }

        return parentTransactionOptional.get().getChildTransaction()
                .stream()
                .map(installment -> buildInstallmentResponseFrom(parentTransactionOptional.get(), installment))
                .collect(Collectors.toList());
    }

    private InstallmentResponse buildInstallmentResponseFrom(ParentTransaction parentTransaction, ChildTransaction installment) {
        return new InstallmentResponse(installment.getId(), parentTransaction.getSender(), parentTransaction.getReceiver(),
                parentTransaction.getTotalAmount(), installment.getPaidAmount());
    }

    private Double totalInstallmentPaid(ParentTransaction parentTransaction) {
        return parentTransaction.getChildTransaction().stream()
                .map(ChildTransaction::getPaidAmount)
                .reduce(Double::sum).orElse(0.0);
    }

    private TransactionResponse buildTransactionResponseFrom(ParentTransaction parentTransaction, Double totalPaidAmount) {
        return new TransactionResponse(parentTransaction.getId(), parentTransaction.getSender(), parentTransaction.getReceiver(),
                parentTransaction.getTotalAmount(), totalPaidAmount);
    }
}
