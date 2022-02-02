package transactions.assmbler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import transactions.controller.TransactionsController;
import transactions.model.TransactionResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransactionsAssembler implements RepresentationModelAssembler<TransactionResponse, EntityModel<TransactionResponse>> {

    @Override
    public EntityModel<TransactionResponse> toModel(TransactionResponse transaction) {
        return EntityModel.of(transaction,
                linkTo(methodOn(TransactionsController.class).getInstallmentsForParentTransaction(transaction.getId()))
                        .withSelfRel());
    }
}
