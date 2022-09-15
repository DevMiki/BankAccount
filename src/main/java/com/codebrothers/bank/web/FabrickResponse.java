package com.codebrothers.bank.web;

import com.codebrothers.bank.web.balance.dto.fabrick.FabrickError;
import com.codebrothers.bank.web.balance.Payload;
import com.codebrothers.bank.web.balance.enumerators.FabrickStatus;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class FabrickResponse<T extends Payload> {

    private FabrickStatus status;
    private List<FabrickError> error;
    private T payload;

}
