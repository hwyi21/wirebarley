package com.example.wirebarley.controller;


import com.example.wirebarley.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ExchangeController.REQUEST_BASE_PATH)
public class ExchangeController {
    static final String REQUEST_BASE_PATH = "/api/exchange";

    @Autowired
    private ExchangeService exchangeService;

    public enum CURRENCY {
        USD,
        KRW,
        JPY,
        PHP
    }

    @GetMapping()
    ResponseEntity<Double> getExchangeRate(
        @RequestParam CURRENCY currency
    ) {
        double rate = exchangeService.getCurrencyRate(currency);
        return ResponseEntity.ok(rate);
    }


}
