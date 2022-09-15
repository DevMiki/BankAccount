package com.codebrothers.bank.core.balance.model.entity;

import com.codebrothers.bank.web.balance.enumerators.TransactionType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TransactionEntity {

    @Id
    @GeneratedValue
    private int id;
    private String transactionId;
    private String operationId;
    private LocalDate accountingDate;
    private LocalDate valueDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Type type;
    private double amount;
    private String currency;
    private String description;

    @Entity
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Type {
        @Id
        @GeneratedValue
        private int id;
        public TransactionType enumeration;
        public String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TransactionType getEnumeration() {
            return enumeration;
        }

        public void setEnumeration(TransactionType enumeration) {
            this.enumeration = enumeration;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


