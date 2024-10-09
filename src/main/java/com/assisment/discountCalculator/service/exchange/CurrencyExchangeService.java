package com.assisment.discountCalculator.service.exchange;

import java.util.Map;

public interface CurrencyExchangeService {
    Map<String, Object> getExchangeRates(String baseCurrency);
}
