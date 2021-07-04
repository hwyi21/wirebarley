package com.example.wirebarley.service;

import com.example.wirebarley.controller.ExchangeController.CURRENCY;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExchangeService {
    static final String ACCESS_KEY = "376d92a03fcf89dba1743a53a95e0d83";
    static final String CURRENCIES = "USD,KRW,JPY,PHP";
    static final String URL = "http://api.currencylayer.com/live";

    private URI buildURI() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("access_key", ACCESS_KEY)
                .queryParam("currencies", CURRENCIES)
                .queryParam("format", 1);

        return builder.build().encode().toUri();
    }

    public double getCurrencyRate(CURRENCY currency){
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI calculateURI = buildURI();

            JsonNode result = restTemplate.getForObject(calculateURI, JsonNode.class);

            JsonNode json = result.get("quotes");

            return Double.parseDouble(json.get("USD"+currency).toString());

        } catch (Exception e){
            throw e;
        }
    }
}
