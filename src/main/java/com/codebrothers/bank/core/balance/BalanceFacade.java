package com.codebrothers.bank.core.balance;

import com.codebrothers.bank.web.balance.dto.MoneyTransferDTO;
import com.codebrothers.bank.web.balance.dto.MoneyTransferResponseDTO;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse.FabrickTransaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface BalanceFacade {
    double readBalance(String accountId);
    MoneyTransferResponseDTO moneyTransfer(String accountId, MoneyTransferDTO moneyTransferDTO);
    List<FabrickTransaction> readTransaction(String accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);
}
