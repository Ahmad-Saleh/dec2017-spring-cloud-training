package com.progressoft.training.paymentsapp.controller;

import com.progressoft.training.paymentsapp.entity.Payment;
import com.progressoft.training.paymentsapp.entity.PaymentTotals;
import com.progressoft.training.paymentsapp.repository.PaymentsRepository;
import com.progressoft.training.paymentsapp.service.ExchangeServiceClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class PaymentTotalsController {

    private PaymentsRepository repository;
    private ExchangeServiceClient exchangeServiceClient;

    public PaymentTotalsController(PaymentsRepository repository, ExchangeServiceClient exchangeServiceClient) {
        this.repository = repository;
        this.exchangeServiceClient = exchangeServiceClient;
    }

    @RequestMapping("/totals")
    public PaymentTotals getTotalsForAccount(@RequestParam String account, @RequestParam String currencyCode) {
        List<Payment> paymentsSent = repository.getPaymentsByPayAccountNumber(account);
        BigDecimal totalAmount = paymentsSent.stream()
                .map(p -> exchangeAmount(p.getAmount(), p.getCurrencyCode(), currencyCode))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return PaymentTotals.builder()
                .accountNumber(account)
                .currency(Currency.getInstance(currencyCode))
                .totalAmountPaid(totalAmount).build();
    }

    private BigDecimal exchangeAmount(BigDecimal amount, String fromCurrencyCode, String toCurrencyCode) {
        return amount.multiply(exchangeServiceClient.getExchangeRate(fromCurrencyCode, toCurrencyCode));
    }

}
