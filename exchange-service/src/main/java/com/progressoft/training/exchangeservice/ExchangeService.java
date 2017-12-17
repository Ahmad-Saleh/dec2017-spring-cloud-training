package com.progressoft.training.exchangeservice;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
public class ExchangeService {

    private static Table<Currency, Currency, BigDecimal> exchangesTable;

    static {
        exchangesTable = HashBasedTable.create();
        exchangesTable.put(Currency.getInstance("JOD"), Currency.getInstance("USD"), new BigDecimal(1.4));
        exchangesTable.put(Currency.getInstance("JOD"), Currency.getInstance("OMR"), new BigDecimal(.5));
        exchangesTable.put(Currency.getInstance("USD"), Currency.getInstance("JOD"), new BigDecimal(0.7));
        exchangesTable.put(Currency.getInstance("USD"), Currency.getInstance("OMR"), new BigDecimal(0.5 * 0.7));
        exchangesTable.put(Currency.getInstance("OMR"), Currency.getInstance("JOD"), new BigDecimal(2));
        exchangesTable.put(Currency.getInstance("OMR"), Currency.getInstance("USD"), new BigDecimal(1 / (0.5 * 0.7)));
    }

    public BigDecimal getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        if (fromCurrencyCode.equals(toCurrencyCode)) {
            return BigDecimal.ONE;
        }
        return exchangesTable.get(Currency.getInstance(fromCurrencyCode), Currency.getInstance(toCurrencyCode));
    }
}