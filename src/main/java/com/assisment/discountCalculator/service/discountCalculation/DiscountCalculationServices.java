package com.assisment.discountCalculator.service.discountCalculation;

import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.model.User;

import java.util.List;
import java.util.Map;

public interface DiscountCalculationServices {
    double calculateDiscount(User user, double totalBill, List<Item> items);
    double convertCurrency(double amount, String targetCurrency, Map<String, Double> exchangeRates);
}
