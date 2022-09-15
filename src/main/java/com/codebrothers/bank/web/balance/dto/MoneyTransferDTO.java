package com.codebrothers.bank.web.balance.dto;

import lombok.Data;

@Data
public class MoneyTransferDTO {

    private Creditor creditor;
    private String description;
    private String currency;
    private double amount;
    private String executionDate;

    @Data
    public static class Creditor{
        private String name;
        private Account account;
        private Address address;

    }

    @Data
    public static class Account{
        private String accountCode;
    }

    @Data
    public static class Address{
        private String address;
        private String city;
        private String countryCode;
    }
}
