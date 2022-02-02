package transactions.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class ParentTransaction {
    @Id
    private Long id;

    private String sender;
    private String receiver;
    private Double totalAmount;

    @OneToMany(mappedBy = "parentTransaction")
    @JsonManagedReference
    private List<ChildTransaction> childTransaction;
}
