package transactions.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import transactions.entity.ParentTransaction;

public interface ParentTransactionRepository extends PagingAndSortingRepository<ParentTransaction, Long> {
}
