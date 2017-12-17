package com.progressoft.training.paymentsapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExchangeServiceClient {

    @Value("${exchange-service.url}")
    private String baseUrl;

    private RestTemplate restTemplate;

    public ExchangeServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("from", fromCurrencyCode);
        parametersMap.put("to", toCurrencyCode);
        return restTemplate.getForEntity(baseUrl, BigDecimal.class, parametersMap).getBody();
    }

}
