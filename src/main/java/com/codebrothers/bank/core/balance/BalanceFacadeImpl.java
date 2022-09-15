package com.codebrothers.bank.core.balance;

import com.codebrothers.bank.core.balance.model.entity.TransactionEntity;
import com.codebrothers.bank.core.balance.model.repository.TransactionRepository;
import com.codebrothers.bank.web.FabrickResponse;
import com.codebrothers.bank.web.balance.dto.MoneyTransferDTO;
import com.codebrothers.bank.web.balance.dto.MoneyTransferResponseDTO;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickBalanceResponse;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickMoneyTransferRequest;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickMoneyTransferResponse;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse;
import com.codebrothers.bank.web.balance.dto.fabrick.FabrickReadTransfersResponse.FabrickTransaction;
import com.codebrothers.bank.web.balance.enumerators.FabrickStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BalanceFacadeImpl implements BalanceFacade {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private Function<FabrickResponse<FabrickReadTransfersResponse>, List<TransactionEntity>> fabrickTransactionToEntity;

    public BalanceFacadeImpl(TransactionRepository transactionRepository,
                             Function<FabrickResponse<FabrickReadTransfersResponse>, List<TransactionEntity>> fabrickTransactionToEntity) {
        this.transactionRepository = transactionRepository;
        this.fabrickTransactionToEntity = fabrickTransactionToEntity;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public double readBalance(String accountId) {
        //Call to service
        String uri = String.format("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/balance", accountId);
        //Making request
        final HttpEntity<Void> request = getStringHttpEntity(HttpMethod.GET, uri).build();
        final ResponseEntity<FabrickResponse<FabrickBalanceResponse>> response = restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<>(){});
        //Response validation
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Something went wrong while reading balance. Http status: %d".formatted(response.getStatusCodeValue()));
        }
        final FabrickResponse<FabrickBalanceResponse> balanceBody = Optional.ofNullable(response.getBody()).orElseThrow();
        if(balanceBody.getStatus() != FabrickStatus.OK){
            throw new RuntimeException("Something went wrong while reading balance: %s".formatted(balanceBody.getError()));
        }
        //return after validation
        return balanceBody.getPayload().getBalance();
    }

    @Override
    public MoneyTransferResponseDTO moneyTransfer(String accountId, MoneyTransferDTO moneyTransferDTO) {

        final FabrickMoneyTransferRequest myRequest = FabrickMoneyTransferRequest.from(moneyTransferDTO);
        //Call to service
        String uri = String.format("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers",accountId);
        //Making request
        final RequestEntity<FabrickMoneyTransferRequest> request = getStringHttpEntity(HttpMethod.POST, uri)
                .header("X-Time-Zone", "")
                .body(myRequest);
        final ResponseEntity<FabrickResponse<FabrickMoneyTransferResponse>> response = restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<>(){});
        //Response validation
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Something went wrong while reading balance. Http status: %d".formatted(response.getStatusCodeValue()));
        }
        final FabrickResponse<FabrickMoneyTransferResponse> moneyTransferBody = Optional.ofNullable(response.getBody()).orElseThrow();
        if(moneyTransferBody.getStatus() != FabrickStatus.OK){
            throw new RuntimeException("Something went wrong while reading balance: %s".formatted(moneyTransferBody.getError()));
        }
        final MoneyTransferResponseDTO moneyTransferResponseDTO = new MoneyTransferResponseDTO();
        moneyTransferResponseDTO.setCode(response.getBody().getPayload().getCode());
        moneyTransferResponseDTO.setCode(response.getBody().getPayload().getDescription());
        return moneyTransferResponseDTO;
    }

    @Override
    public List<FabrickTransaction> readTransaction(String accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        //Call to service
        String uri = String.format("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/%s/transactions", accountId);
        final String finalUri = UriComponentsBuilder.fromUriString(uri)
                .queryParam("fromAccountingDate",String.valueOf(fromAccountingDate))
                .queryParam("toAccountingDate", String.valueOf(toAccountingDate))
                .toUriString();
        final HttpEntity<Void> request = getStringHttpEntity(HttpMethod.GET, finalUri).build();
        final ResponseEntity<FabrickResponse<FabrickReadTransfersResponse>> response = restTemplate.exchange(finalUri, HttpMethod.GET, request, new ParameterizedTypeReference<>(){});
        //Response validation
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Something went wrong while reading balance. Http status: %d".formatted(response.getStatusCodeValue()));
        }
        final FabrickResponse<FabrickReadTransfersResponse> transactionBody = Optional.ofNullable(response.getBody()).orElseThrow();
        if(transactionBody.getStatus() != FabrickStatus.OK){
            throw new RuntimeException("Something went wrong while reading balance: %s".formatted(transactionBody.getError()));
        }
        final List<TransactionEntity> transactionEntities = fabrickTransactionToEntity.apply(transactionBody);
        transactionRepository.saveAll(transactionEntities);
        //return after validation
        return transactionBody.getPayload().getList();
    }

    private static BodyBuilder getStringHttpEntity(HttpMethod httpMethod, String uri) {
        return RequestEntity.method(httpMethod, uri)
                .header("Content-Type","application/json")
                .header("Auth-Schema","S2S")
                .header("Api-Key","FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
    }
}
