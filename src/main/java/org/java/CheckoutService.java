package org.java;

import java.math.BigDecimal;

public class CheckoutService {
    public BigDecimal purchase(String productName, String customerId) {
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        return paymentProcessor.chargeCustomer(customerId, BigDecimal.TEN);
    }
}
