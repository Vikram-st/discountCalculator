package com.assisment.currency_discount_calculator.service.exchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyExchangeService {

    @Value("${exchange.api.key}")
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //@Cacheable(value = "exchangeRates", key = "#baseCurrency")
    public Map<String, Object>  getExchangeRates(String baseCurrency) {
            String url = String.format("https://open.er-api.com/v6/latest/%s?apikey=%s", baseCurrency, apiKey);
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to fetch exchange rates: " + e.getMessage());
        }
    }
}
