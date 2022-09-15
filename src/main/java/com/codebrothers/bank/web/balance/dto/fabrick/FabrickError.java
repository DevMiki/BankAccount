package com.codebrothers.bank.web.balance.dto.fabrick;

import lombok.Data;

@Data
public class FabrickError {

    private String code;
    private String description;
    private String params;

}
