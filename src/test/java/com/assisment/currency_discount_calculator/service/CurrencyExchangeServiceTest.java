package com.assisment.currency_discount_calculator.service;

import com.assisment.currency_discount_calculator.service.exchange.CurrencyExchangeService;
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
class CurrencyExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    private final String apiKey = "dummyApiKey"; // Set the test API key


    @Test
    void testGetExchangeRatesSuccess() {
        // Arrange
        String baseCurrency = "USD";
        String url = String.format("https://open.er-api.com/v6/latest/%s?apikey=%s", baseCurrency, apiKey);

        // Mocking the response from the API
        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.85);
        mockResponse.put("rates", rates);

        // Mock the RestTemplate behavior
        doReturn(mockResponse).when(restTemplate).getForObject(anyString(), eq(Map.class));

        // Act
        Map<String, Object> result = currencyExchangeService.getExchangeRates(baseCurrency);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsKey("rates"));
        Assertions.assertEquals(0.85, ((Map<String, Double>) result.get("rates")).get("EUR"));
    }

    @Test
    void testGetExchangeRatesFailure() {
        // Arrange
        String baseCurrency = "USD";
        String url = String.format("https://open.er-api.com/v6/latest/%s?apikey=%s", baseCurrency, apiKey);

        // Mock a client error exception
        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
                .when(restTemplate).getForObject(anyString(), eq(Map.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            currencyExchangeService.getExchangeRates(baseCurrency);
        });

        Assertions.assertTrue(exception.getMessage().contains("Failed to fetch exchange rates"));
    }
}
