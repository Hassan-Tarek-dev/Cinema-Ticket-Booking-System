package com.cinema.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// STRATEGY PATTERN
public class CashPaymentStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(CashPaymentStrategy.class);

    @Override
    public boolean processPayment(Double amount, String transactionDetails) {
        log.info("Processing Cash payment of amount: {}", amount);
        
        if (!validatePaymentDetails(transactionDetails)) {
            log.error("Invalid cash payment details");
            return false;
        }

        try {
            log.info("Waiting for cash verification at counter...");
            Thread.sleep(1000);
            
            log.info("Cash payment verified successfully for amount: {}", amount);
            return true;
        } catch (InterruptedException e) {
            log.error("Cash payment verification interrupted", e);
            return false;
        }
    }

    @Override
    public boolean validatePaymentDetails(String paymentDetails) {
        log.info("Validating cash payment details");
        return paymentDetails != null && !paymentDetails.isEmpty();
    }

    @Override
    public String getPaymentMethodName() {
        return "CASH";
    }

    @Override
    public boolean refundPayment(Double amount, String transactionId) {
        log.info("Processing Cash refund of amount: {} with transaction ID: {}", amount, transactionId);
        try {
            Thread.sleep(500);
            log.info("Cash refund processed successfully");
            return true;
        } catch (InterruptedException e) {
            log.error("Refund processing interrupted", e);
            return false;
        }
    }
}
