package com.assisment.discountCalculator.service;

import static com.assisment.discountCalculator.model.UserType.CUSTOMER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.assisment.discountCalculator.model.Item;
import com.assisment.discountCalculator.model.User;
import com.assisment.discountCalculator.model.UserType;
import com.assisment.discountCalculator.service.discountCalculation.DiscountCalculationServicesImpl;
import com.assisment.discountCalculator.service.discountStrategy.impl.AffiliateDiscountStrategy;
import com.assisment.discountCalculator.service.discountStrategy.impl.CustomerDiscountStrategy;
import com.assisment.discountCalculator.service.discountStrategy.impl.EmployeeDiscountStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

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
        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserType.EMPLOYEE);

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),
                new Item(2L, "toys","NON_GROCERIES", 200)
        );

        double totalBill = 350.0;
        when(employeeDiscountStrategy.calculateDiscount(totalBill)).thenReturn(250.0);
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);
        assertEquals(250.0, finalAmount);
        verify(employeeDiscountStrategy).calculateDiscount(totalBill);
    }

    @Test
    void calculateDiscount_AffiliateDiscount() {
        User user = new User();
        user.setUserId(2L);
        user.setUserType(UserType.AFFILIATE);

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),
                new Item(2L, "toys","NON_GROCERIES", 200)
        );

        double totalBill = 350.0;
        when(affiliateDiscountStrategy.calculateDiscount(totalBill)).thenReturn(300.0);
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);
        assertEquals(300.0, finalAmount);
        verify(affiliateDiscountStrategy).calculateDiscount(totalBill);
    }

    @Test
    void calculateDiscount_CustomerDiscount() {
        User user = new User();
        user.setUserId(3L);
        user.setUserType(CUSTOMER);
        user.setCustomerTenure(3);
        List<Item> items = Arrays.asList(
                new Item(1L,"Laptop", "NON_GROCERIES", 1200.0),
                new Item(2L,"Monitor", "NON_GROCERIES", 250.0)
        );
        double totalBill = 1450.0;
        when(customerDiscountStrategy.calculateDiscount(totalBill)).thenReturn(1377.5);
        double result = discountCalculationServices.calculateDiscount(user, totalBill, items);
        assertEquals(1377.5, result, 0.01);
    }

    @Test
    void calculateDiscount_NoDiscount() {
        User user = new User();
        user.setUserId(4L);
        user.setUserType(CUSTOMER);

        List<Item> items = Arrays.asList(
                new Item(1L,"Biscuit", "GROCERIES", 150),
                new Item(2L, "toys","NON_GROCERIES", 200)
        );

        double totalBill = 350.0;
        double finalAmount = discountCalculationServices.calculateDiscount(user, totalBill, items);
        assertEquals(350.0, finalAmount);
    }
}
