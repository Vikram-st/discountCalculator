package com.assisment.discountCalculator.controller;

import com.assisment.discountCalculator.model.*;
import com.assisment.discountCalculator.service.discountCalculation.DiscountCalculationServices;
import com.assisment.discountCalculator.service.exchange.CurrencyExchangeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BillControllerTest {

    @InjectMocks
    private BillController billController;

    @Mock
    private DiscountCalculationServices discountCalculationServices;

    @Mock
    private CurrencyExchangeServiceImpl currencyExchangeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculate_Success() {
        // Given
        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserType.CUSTOMER);

        List<Item> items = Arrays.asList(
                new Item(1L,"Parley-G Biscuit", "GROCERIES", 150),  // Ensure category is set
                new Item(2L, "Haldiram Namkeen","GROCERIES", 200) // Ensure category is set
        );

        BillRequest request = new BillRequest();
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("EUR");
        request.setUser(user);
        request.setItems(items);

        Map<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("EUR", 0.85);

        when(currencyExchangeServiceImpl.getExchangeRates("USD")).thenReturn(Map.of("rates", exchangeRates));
        when(discountCalculationServices.calculateDiscount(user, 350, items)).thenReturn(300.0);

        BillResponse response = billController.calculate(request);
    }

    @Test
    void calculate_InvalidCurrency() {
        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserType.CUSTOMER);

        List<Item> items = Arrays.asList(new Item(1L,"Parley-G Biscuit", "GROCERIES", 150));

        BillRequest request = new BillRequest();
        request.setOriginalCurrency("INVALID_CURRENCY");
        request.setTargetCurrency("EUR");
        request.setUser(user);
        request.setItems(items);

        when(currencyExchangeServiceImpl.getExchangeRates("INVALID_CURRENCY")).thenThrow(new RuntimeException("Currency not found"));

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> billController.calculate(request));
        assertEquals("Currency not found", exception.getMessage());

        verify(currencyExchangeServiceImpl).getExchangeRates("INVALID_CURRENCY");
        verify(discountCalculationServices, never()).calculateDiscount(any(), anyDouble(), anyList());
    }
}
