package com.assisment.currency_discount_calculator.controller;

import com.assisment.currency_discount_calculator.model.BillRequest;
import com.assisment.currency_discount_calculator.model.BillResponse;
import com.assisment.currency_discount_calculator.model.Item;
import com.assisment.currency_discount_calculator.service.discountCalculation.DiscountCalculationServices;
import com.assisment.currency_discount_calculator.service.exchange.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.assisment.currency_discount_calculator.utility.Constants.DATEFORMATTER;
import static com.assisment.currency_discount_calculator.utility.Constants.GROCERIES;

@RestController
@RequestMapping("/api")
public class BillController {
    private final DiscountCalculationServices discountService;
    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public BillController(DiscountCalculationServices discountService, CurrencyExchangeService currencyExchangeService) {
        this.discountService = discountService;
        this.currencyExchangeService = currencyExchangeService;
    }

    @PostMapping(value = "/calculate", consumes = "application/json", produces = "application/json")
    public BillResponse calculate(@RequestBody BillRequest request) {
        // Fetch exchange rates
        Map<String, Object> exchangeRates = currencyExchangeService.getExchangeRates(request.getOriginalCurrency());
        Map<String, Double> rates = (Map<String, Double>) exchangeRates.get("rates");

        // Calculate the discount
        double totalAmount = request.getTotalAmount();
        double finalAmount = discountService.calculateDiscount(request.getUser(), totalAmount, request.getItems());

        // Convert the final amount to the target currency
        double convertedAmount = discountService.convertCurrency(finalAmount, request.getTargetCurrency(), rates);

        // Separate grocery and non-grocery items
        List<Item> groceryItems = request.getItems().stream()
                .filter(item -> item.getCategory().equals(GROCERIES))
                .collect(Collectors.toList());

        List<Item> nonGroceryItems = request.getItems().stream()
                .filter(item -> !item.getCategory().equals(GROCERIES))
                .collect(Collectors.toList());

        // Calculate totals for grocery and non-grocery items
        double groceryTotal = groceryItems.stream().mapToDouble(Item::getPrice).sum();
        double nonGroceryTotal = nonGroceryItems.stream().mapToDouble(Item::getPrice).sum();

        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEFORMATTER);

        String biilId = current.format(formatter);
        // Create the response
        BillResponse response = new BillResponse();
        response.setBillId(biilId);
        response.setFinalAmount(convertedAmount);
        response.setGroceryItems(groceryItems);
        response.setNonGroceryItems(nonGroceryItems);
        response.setGroceryTotal(groceryTotal);
        response.setNonGroceryTotal(nonGroceryTotal);

        return response;
    }
}
