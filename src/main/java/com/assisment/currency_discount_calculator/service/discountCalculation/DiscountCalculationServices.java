package com.assisment.currency_discount_calculator.service.discountCalculation;

import com.assisment.currency_discount_calculator.model.Item;
import com.assisment.currency_discount_calculator.model.User;

import java.util.List;
import java.util.Map;

public interface DiscountCalculationServices {
    double calculateDiscount(User user, double totalBill, List<Item> items);
    double convertCurrency(double amount, String targetCurrency, Map<String, Double> exchangeRates);
}
