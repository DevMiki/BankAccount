package com.codebrothers.bank.web.balance.dto.fabrick;

import com.codebrothers.bank.web.balance.dto.MoneyTransferDTO;
import lombok.Data;

//Class creadted in order to achieve loose coupling between the API request and my DTO
@Data
public class FabrickMoneyTransferRequest {

    private Creditor creditor;
    private String description;
    private String currency;
    private double amount;
    private String executionDate;

    public static FabrickMoneyTransferRequest from(MoneyTransferDTO moneyTransferDTO){
        final FabrickMoneyTransferRequest fabrickMoneyTransferRequest = new FabrickMoneyTransferRequest();
        fabrickMoneyTransferRequest.setCreditor(Creditor.from(moneyTransferDTO.getCreditor()));
        fabrickMoneyTransferRequest.setDescription(moneyTransferDTO.getDescription());
        fabrickMoneyTransferRequest.setCurrency(moneyTransferDTO.getCurrency());
        fabrickMoneyTransferRequest.setAmount(moneyTransferDTO.getAmount());
        fabrickMoneyTransferRequest.setExecutionDate(moneyTransferDTO.getExecutionDate());
        return fabrickMoneyTransferRequest;
    }
    @Data
    public static class Creditor{
        private String name;
        private Account account;
        private Address address;

        public static Creditor from(MoneyTransferDTO.Creditor creditor){
            final Creditor c = new Creditor();
            c.setName(creditor.getName());
            c.setAccount(Account.from(creditor.getAccount()));
            c.setAddress(Address.from(creditor.getAddress()));
            return c;
        }
    }

    @Data
    public static class Account{
        private String accountCode;

        public static Account from(MoneyTransferDTO.Account account){
            final Account a = new Account();
            a.setAccountCode(account.getAccountCode());
            return a;
        }
    }

    @Data
    public static class Address{
        private String address;
        private String city;
        private String countryCode;

        public static Address from(MoneyTransferDTO.Address address){
            final Address a = new Address();
            a.setAddress(address.getAddress());
            a.setCity(address.getCity());
            a.setCountryCode(address.getCountryCode());
            return a;
        }
    }
}
