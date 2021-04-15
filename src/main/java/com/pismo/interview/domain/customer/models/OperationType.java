package com.pismo.interview.domain.customer.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "operation_types")
public class OperationType implements Serializable {

    private static final Set<String> WITHDRAW = Set.of("COMPRA_A_VISTA", "COMPRA_PARCELADA");
    private static final Set<String> PURCHASE = Set.of("SAQUE");
    private static final Set<String> PAYMENT = Set.of("PAGAMENTO");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_type_id")
    private Long id;

    @Column(name = "description")
    private String description;

    public boolean isWithdrawOrPurchase() {
        return WITHDRAW.contains(this.description) || PURCHASE.contains(this.description);
    }

    public boolean isPayment() {
        return PAYMENT.contains(this.description);
    }

    public boolean isNotAllowed() {
        return !(isWithdrawOrPurchase() || isPayment());
    }
}
