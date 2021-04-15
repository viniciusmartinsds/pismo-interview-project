package com.pismo.interview.domain.customer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name="operation_type_id")
    private OperationType operationType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "event_date")
    private Instant eventDate;

    public Transaction(Account account, OperationType operationType, BigDecimal amount, Instant eventDate) {
        this.account = account;
        this.operationType = operationType;
        this.amount = amount;
        this.eventDate = eventDate;
    }
}
