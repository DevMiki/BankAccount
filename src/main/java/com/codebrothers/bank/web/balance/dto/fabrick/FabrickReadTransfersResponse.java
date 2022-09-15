package com.codebrothers.bank.web.balance.dto.fabrick;

import com.codebrothers.bank.core.balance.model.entity.TransactionEntity;
import com.codebrothers.bank.web.balance.Payload;
import com.codebrothers.bank.web.balance.enumerators.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class FabrickReadTransfersResponse implements Payload {

    List<FabrickTransaction> list;

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class FabrickTransaction {
        private String transactionId;
        private String operationId;
        private LocalDate accountingDate;
        private LocalDate valueDate;
        private Type type;
        private double amount;
        private String currency;
        private String description;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class Type {
        public TransactionType enumeration;
        public String value;

        public static TransactionEntity.Type from(Type fabrickType) {
            final TransactionEntity.Type entityType = new TransactionEntity.Type();
            entityType.setEnumeration(fabrickType.getEnumeration());
            entityType.setValue(fabrickType.getValue());
            return entityType;
        }
    }

}
