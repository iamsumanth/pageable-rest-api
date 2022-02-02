package transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "transactions")
public class TransactionResponse {
    private Long id;
    private String sender;
    private String receiver;
    private Double totalAmount;
    private Double totalPaidAmount;
}
