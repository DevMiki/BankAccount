package com.codebrothers.bank.core.balance.model;

import com.codebrothers.bank.core.balance.model.entity.TransactionEntity;
import com.codebrothers.bank.web.FabrickResponse;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse;
import com.codebrothers.bank.web.balance.enumerators.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

class FabrickTransactionToEntityTest {


    @Test
    void isPossibleToMapfabrickReadTransfersResponseIntoListTransactionEntity() {

        final FabrickReadTransfersResponse.FabrickTransaction transaction = FabrickReadTransfersResponse.FabrickTransaction.of("1",
                "20",
                LocalDate.of(2022, 8, 8),
                LocalDate.of(2022, 8, 8),
                FabrickReadTransfersResponse.Type.of(TransactionType.GBS_TRANSACTION_TYPE, "GBS_ACCOUNT_TRANSACTION_TYPE_0009"),
                2.2,
                "EUR",
                "Description"
        );
        final List<FabrickReadTransfersResponse.FabrickTransaction> fabrickTransactions = Collections.singletonList(transaction);
        final FabrickReadTransfersResponse fabrickReadTransfersResponse = FabrickReadTransfersResponse.of(fabrickTransactions);
        final FabrickResponse<FabrickReadTransfersResponse> fabrickInput = new FabrickResponse<>();
        fabrickInput.setPayload(fabrickReadTransfersResponse);
        final TransactionEntity transactionEntity = TransactionEntity.of(0,
                "1",
                "20",
                LocalDate.of(2022, 8, 8),
                LocalDate.of(2022, 8, 8),
                TransactionEntity.Type.of(0, TransactionType.GBS_TRANSACTION_TYPE, "GBS_ACCOUNT_TRANSACTION_TYPE_0009"),
                2.2,
                "EUR",
                "Description");
        final List<TransactionEntity> expected = Collections.singletonList(transactionEntity);

        final FabrickTransactionToEntity fabrickTransactionToEntity = new FabrickTransactionToEntity();
        final List<TransactionEntity> actual = fabrickTransactionToEntity.apply(fabrickInput);
        Assertions.assertEquals(expected, actual);
    }
}