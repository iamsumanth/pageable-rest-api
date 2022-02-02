package transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentResponse {
    private Long id;
    private String sender;
    private String receiver;
    private Double totalAmount;
    private Double paidAmount;
}
