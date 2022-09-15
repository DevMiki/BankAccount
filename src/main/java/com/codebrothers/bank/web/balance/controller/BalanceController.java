package com.codebrothers.bank.web.balance.controller;

import com.codebrothers.bank.core.balance.BalanceFacade;
import com.codebrothers.bank.web.balance.dto.MoneyTransferDTO;
import com.codebrothers.bank.web.balance.dto.MoneyTransferResponseDTO;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse.FabrickTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BalanceController {

    @Autowired
    private BalanceFacade balanceFacade;

    @GetMapping("{accountId}/balance")
    public double readBalance(@PathVariable String accountId){
        return balanceFacade.readBalance(accountId);
    }

    @PostMapping("{accountId}/moneytransfer")
    public MoneyTransferResponseDTO makeMoneyTransfer(@PathVariable String accountId, @RequestBody MoneyTransferDTO moneyTransferDTO){
        return balanceFacade.moneyTransfer(accountId, moneyTransferDTO);
    }

    @GetMapping("{accountId}/transactions")
    public List<FabrickTransaction> readTransactions(@PathVariable String accountId,
                                                     @RequestParam(name = "fromAccountingDate") LocalDate fromAccountingDate,
                                                     @RequestParam(name = "toAccountingDate")  LocalDate toAccountingDate){
        return balanceFacade.readTransaction(accountId, fromAccountingDate, toAccountingDate);
    }
}
