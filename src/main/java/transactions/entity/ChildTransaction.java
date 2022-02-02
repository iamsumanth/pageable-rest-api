package transactions.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChildTransaction {
    @Id
    private Long id;

    @ManyToOne
    @JsonBackReference
    private ParentTransaction parentTransaction;
    private Double paidAmount;
}
