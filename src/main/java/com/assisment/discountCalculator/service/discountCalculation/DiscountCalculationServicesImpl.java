package com.assisment.discountCalculator.service.discountCalculation;

import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.model.User;
import com.assisment.discountCalculator.model.UserType;
import com.assisment.discountCalculator.service.discountStrategy.DiscountStrategy;
import com.assisment.discountCalculator.service.discountStrategy.impl.AffiliateDiscountStrategy;
import com.assisment.discountCalculator.service.discountStrategy.impl.CustomerDiscountStrategy;
import com.assisment.discountCalculator.service.discountStrategy.impl.EmployeeDiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscountCalculationServicesImpl implements DiscountCalculationServices {

    private final Map<UserType, DiscountStrategy> discountStrategies;

    public DiscountCalculationServicesImpl(EmployeeDiscountStrategy employeeDiscountStrategy,
                                           AffiliateDiscountStrategy affiliateDiscountStrategy,
                                           CustomerDiscountStrategy customerDiscountStrategy) {
        discountStrategies = new HashMap<>();
        discountStrategies.put(UserType.EMPLOYEE, employeeDiscountStrategy);
        discountStrategies.put(UserType.AFFILIATE, affiliateDiscountStrategy);
        discountStrategies.put(UserType.CUSTOMER, customerDiscountStrategy);
    }


    @Override
    public double calculateDiscount(User user, double totalBill, List<Item> items) {
        DiscountStrategy strategy = discountStrategies.get(user.getUserType());
        return strategy != null ? strategy.calculateDiscount(totalBill,items ) : totalBill;
    }

    @Override
    public double convertCurrency(double amount, String targetCurrency, Map<String, Double> exchangeRates) {
        Double rate = exchangeRates.get(targetCurrency);
        if (rate == null) {
            throw new IllegalArgumentException("Invalid target currency: " + targetCurrency);
        }
        return amount * rate;
    }
}
