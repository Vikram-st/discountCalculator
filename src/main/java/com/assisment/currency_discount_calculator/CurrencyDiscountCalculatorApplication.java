package com.assisment.currency_discount_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class CurrencyDiscountCalculatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurrencyDiscountCalculatorApplication.class, args);
	}
}

