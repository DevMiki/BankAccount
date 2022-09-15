package com.codebrothers.bank.web.balance.dto.fabrick;

import com.codebrothers.bank.web.balance.Payload;
import lombok.Data;

@Data
public class FabrickMoneyTransferResponse implements Payload {

    private String code;
    private String description;

}
