package com.codebrothers.bank.core.balance.model;

import com.codebrothers.bank.core.balance.model.entity.TransactionEntity;
import com.codebrothers.bank.web.FabrickResponse;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse.FabrickTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class FabrickTransactionToEntity implements Function<FabrickResponse<FabrickReadTransfersResponse>, List<TransactionEntity>> {

    @Override
    public List<TransactionEntity> apply(FabrickResponse<FabrickReadTransfersResponse> fabrickReadTransfersResponse) {
        final ArrayList<TransactionEntity> entities = new ArrayList<>();
        final TransactionEntity transactionEntity = new TransactionEntity();

        for(FabrickTransaction fabrickTransaction : fabrickReadTransfersResponse.getPayload().getList()){
            transactionEntity.setOperationId(fabrickTransaction.getOperationId());
            transactionEntity.setTransactionId(fabrickTransaction.getTransactionId());
            transactionEntity.setAmount(fabrickTransaction.getAmount());
            transactionEntity.setDescription(fabrickTransaction.getDescription());
            transactionEntity.setCurrency(fabrickTransaction.getCurrency());
            transactionEntity.setType(FabrickReadTransfersResponse.Type.from(fabrickTransaction.getType()));
            transactionEntity.setValueDate(fabrickTransaction.getValueDate());
            transactionEntity.setAccountingDate(fabrickTransaction.getAccountingDate());
            transactionEntity.setCurrency(fabrickTransaction.getCurrency());
            entities.add(transactionEntity);
        }
        return entities;
    }
}
