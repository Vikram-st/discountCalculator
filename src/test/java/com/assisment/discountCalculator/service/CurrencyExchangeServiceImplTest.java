package com.assisment.discountCalculator.service;

import com.assisment.discountCalculator.service.exchange.CurrencyExchangeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeServiceImpl;

    private final String apiKey = "dummyApiKey"; // Set the test API key


    @Test
    void testGetExchangeRatesSuccess() {
        String baseCurrency = "USD";
        String url = String.format("https://open.er-api.com/v6/latest/%s?apikey=%s", baseCurrency, apiKey);

        // Mocking the response from the API
        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.85);
        mockResponse.put("rates", rates);

        // Mock the RestTemplate behavior
        doReturn(mockResponse).when(restTemplate).getForObject(anyString(), eq(Map.class));

        Map<String, Object> result = currencyExchangeServiceImpl.getExchangeRates(baseCurrency);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsKey("rates"));
        Assertions.assertEquals(0.85, ((Map<String, Double>) result.get("rates")).get("EUR"));
    }

    @Test
    void testGetExchangeRatesFailure() {
        String baseCurrency = "USD";
        String url = String.format("https://open.er-api.com/v6/latest/%s?apikey=%s", baseCurrency, apiKey);

        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
                .when(restTemplate).getForObject(anyString(), eq(Map.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            currencyExchangeServiceImpl.getExchangeRates(baseCurrency);
        });

        Assertions.assertTrue(exception.getMessage().contains("Failed to fetch exchange rates"));
    }
}
