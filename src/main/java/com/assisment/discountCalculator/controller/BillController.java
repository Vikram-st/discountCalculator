package com.assisment.discountCalculator.controller;

import com.assisment.discountCalculator.model.BillRequest;
import com.assisment.discountCalculator.model.BillResponse;
import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.service.discountCalculation.DiscountCalculationServices;
import com.assisment.discountCalculator.service.exchange.CurrencyExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.assisment.discountCalculator.utility.Constants.DATEFORMATTER;
import static com.assisment.discountCalculator.utility.Constants.GROCERIES;

@RestController
@RequestMapping("/api")
public class BillController {
    private final DiscountCalculationServices discountService;
    private final CurrencyExchangeServiceImpl currencyExchangeServiceImpl;

    @Autowired
    public BillController(DiscountCalculationServices discountService, CurrencyExchangeServiceImpl currencyExchangeServiceImpl) {
        this.discountService = discountService;
        this.currencyExchangeServiceImpl = currencyExchangeServiceImpl;
    }

    @PostMapping(value = "/calculate", consumes = "application/json", produces = "application/json")
    public BillResponse calculate(@RequestBody BillRequest request) {
        // Fetch exchange rates
        Map<String, Object> exchangeRates = currencyExchangeServiceImpl.getExchangeRates(request.getOriginalCurrency());
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
