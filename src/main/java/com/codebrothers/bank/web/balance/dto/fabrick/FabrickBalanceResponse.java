package com.codebrothers.bank.web.balance.dto.fabrick;

import com.codebrothers.bank.web.balance.Payload;
import lombok.Data;

import java.util.Date;

@Data
public class FabrickBalanceResponse implements Payload {
    private Date date;
    private double balance;
    private double availableBalance;
    private String currency;
}
