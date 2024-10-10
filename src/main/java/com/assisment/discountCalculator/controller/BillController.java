package com.assisment.discountCalculator.controller;

import com.assisment.discountCalculator.model.BillCalculationResult;
import com.assisment.discountCalculator.model.BillRequest;
import com.assisment.discountCalculator.model.BillResponse;
import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.service.discountCalculation.DiscountCalculationServices;
import com.assisment.discountCalculator.service.exchange.CurrencyExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.assisment.discountCalculator.utility.Constants.GROCERIES;
import static com.assisment.discountCalculator.utility.Constants.NON_GROCERIES;

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
        Map<String, Object> exchangeRates = currencyExchangeServiceImpl.getExchangeRates(request.getOriginalCurrency());
        Map<String, Double> rates = (Map<String, Double>) exchangeRates.get("rates");

        BillCalculationResult calculationResult = processItemsAndCalculateBill(request);
        double finalBillAmount = calculationResult.getFinalAmount();

        // Convert finalBillAmount to target currency
       //double totalBillPayableInTargetCurrency = discountService.convertCurrency(finalBillAmount, request.getTargetCurrency(), rates);

        BillResponse response = new BillResponse();
        response.setGroceryItems(calculationResult.getGroceryItems());
        response.setNonGroceryItems(calculationResult.getNonGroceryItems());
        response.setGroceryTotal(calculationResult.getGroceryTotal());
        response.setNonGroceryTotal(calculationResult.getNonGroceryTotal());
        response.setFinalAmount(finalBillAmount);
        return response;
    }

    private BillCalculationResult processItemsAndCalculateBill(BillRequest request) {
        // Separate grocery and non-grocery items
        List<Item> groceryItems = request.getItems().stream()
                .filter(item -> item.getCategory().equals(GROCERIES))
                .collect(Collectors.toList());

        List<Item> nonGroceryItems = request.getItems().stream()
                .filter(item -> item.getCategory().equals(NON_GROCERIES))
                .collect(Collectors.toList());

        // Calculate totals for grocery and non-grocery items
        double groceryBill = groceryItems.stream().mapToDouble(Item::getPrice).sum();
        double nonGroceryTotal = nonGroceryItems.stream().mapToDouble(Item::getPrice).sum();

        // Calculate discounted amount for non-grocery items
        double nonGroceryBill = discountService.calculateDiscount(request.getUser(), nonGroceryTotal, request.getItems());

        // Calculate the final amount (with flat discount)
        double totalBill = nonGroceryBill + groceryBill;
        double finalAmount =  totalBill - Math.floor(totalBill / 100) * 5;
        return new BillCalculationResult(groceryItems, nonGroceryItems, groceryBill, nonGroceryTotal, finalAmount);
    }

}
