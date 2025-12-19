package com.cinema.payment;

// STRATEGY PATTERN
public interface PaymentStrategy {
    boolean processPayment(Double amount, String transactionDetails);

    boolean validatePaymentDetails(String paymentDetails);

    String getPaymentMethodName();

    boolean refundPayment(Double amount, String transactionId);
}
