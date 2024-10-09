package com.assisment.currency_discount_calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.assisment.currency_discount_calculator.model.Item;
import com.assisment.currency_discount_calculator.model.User;
import com.assisment.currency_discount_calculator.model.UserType;
import com.assisment.currency_discount_calculator.service.discountCalculation.DiscountCalculationServicesImpl;
import com.assisment.currency_discount_calculator.service.discountStrategy.DiscountStrategy;
import com.assisment.currency_discount_calculator.service.discountStrategy.impl.AffiliateDiscountStrategy;
import com.assisment.currency_discount_calculator.service.discountStrategy.impl.CustomerDiscountStrategy;
import com.assisment.currency_discount_calculator.service.discountStrategy.impl.EmployeeDiscountStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DiscountCalculationServicesImplTest {

    @InjectMocks
    private DiscountCalculationServicesImpl discountCalculationServices;

    @Mock
    private EmployeeDiscountStrategy employeeDiscountStrategy;

    @Mock
    private AffiliateDiscountStrategy affiliateDiscountStrategy;

    @Mock
    private CustomerDiscountStrategy customerDiscountStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        discountCalculationServices = new DiscountCalculationServicesImpl(employeeDiscountStrategy, affiliateDiscountStrategy, customerDiscountStrategy);
    }

    @Test
    void calculateDiscount_EmployeeDiscount() {
        // Given
        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserType.EMPLOYEE);

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),  // Ensure category is set
                new Item(2L, "toys","NON_GROCERIES", 200) // Ensure category is set
        );

        double totalBill = 350.0;

        // Assuming employee discount is 30%
        when(employeeDiscountStrategy.calculateDiscount(totalBill, items)).thenReturn(250.0);

        // When
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);

        // Then
        assertEquals(250.0, finalAmount);
        verify(employeeDiscountStrategy).calculateDiscount(totalBill, items);
    }

    @Test
    void calculateDiscount_AffiliateDiscount() {
        // Given
        User user = new User();
        user.setUserId(2L);
        user.setUserType(UserType.AFFILIATE);

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),  // Ensure category is set
                new Item(2L, "toys","NON_GROCERIES", 200) // Ensure category is set
        );

        double totalBill = 350.0;

        // Assuming affiliate discount is 10%
        when(affiliateDiscountStrategy.calculateDiscount(totalBill, items)).thenReturn(300.0);

        // When
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);

        // Then
        assertEquals(300.0, finalAmount);
        verify(affiliateDiscountStrategy).calculateDiscount(totalBill, items);
    }

    @Test
    void calculateDiscount_CustomerDiscount() {
        // Given
        User user = new User();
        user.setUserId(3L);
        user.setUserType(UserType.CUSTOMER);

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),  // Ensure category is set
                new Item(2L, "toys","NON_GROCERIES", 200) // Ensure category is set
        );

        double totalBill = 350.0;

        // Assuming customer discount is 5% for non-grocery items
        when(customerDiscountStrategy.calculateDiscount(totalBill, items)).thenReturn(330.0);

        // When
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);

        // Then
        assertEquals(330.0, finalAmount);
        verify(customerDiscountStrategy).calculateDiscount(totalBill, items);
    }

    @Test
    void calculateDiscount_NoDiscount() {
        // Given
        User user = new User();
        user.setUserId(4L);
        user.setUserType(null); // No user type means no discount

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),  // Ensure category is set
                new Item(2L, "toys","NON_GROCERIES", 200) // Ensure category is set
        );

        double totalBill = 350.0;

        // When
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);

        // Then
        assertEquals(350.0, finalAmount); // No discount applied
    }
}
