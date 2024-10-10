package com.assisment.discountCalculator.service.discountStrategy.impl;

import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.model.User;
import com.assisment.discountCalculator.service.discountStrategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.assisment.discountCalculator.utility.Constants.GROCERIES;

@Service
public class CustomerDiscountStrategy implements DiscountStrategy {
    private static final double LONG_TERM_CUSTOMER_DISCOUNT = 0.05;

    @Override
    public double calculateDiscount(double totalBill) {
        double discount = 0.05;
        return (totalBill * (1 - discount));
    }

}
