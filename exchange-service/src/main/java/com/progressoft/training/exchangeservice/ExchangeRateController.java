package com.progressoft.training.exchangeservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ExchangeRateController {

    private ExchangeService exchangeService;

    public ExchangeRateController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @RequestMapping("fxRate")
    public BigDecimal getExchangeRate(String from, String to){
        return exchangeService.getExchangeRate(from, to);
    }
}
