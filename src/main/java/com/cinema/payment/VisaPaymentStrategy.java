package com.cinema.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// STRATEGY PATTERN
public class VisaPaymentStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(VisaPaymentStrategy.class);

    @Override
    public boolean processPayment(Double amount, String transactionDetails) {
        log.info("Processing Visa payment of amount: {}", amount);
        
        if (!validatePaymentDetails(transactionDetails)) {
            log.error("Invalid Visa card details");
            return false;
        }

        try {
            log.info("Connecting to Visa payment gateway...");
            Thread.sleep(500);
            
            log.info("Visa payment processed successfully for amount: {}", amount);
            return true;
        } catch (InterruptedException e) {
            log.error("Payment processing interrupted", e);
            return false;
        }
    }

    @Override
    public boolean validatePaymentDetails(String cardDetails) {
        log.info("Validating Visa card details");
        return cardDetails != null && cardDetails.length() >= 19;
    }

    @Override
    public String getPaymentMethodName() {
        return "VISA";
    }

    @Override
    public boolean refundPayment(Double amount, String transactionId) {
        log.info("Processing Visa refund of amount: {} with transaction ID: {}", amount, transactionId);
        try {
            Thread.sleep(500);
            log.info("Visa refund processed successfully");
            return true;
        } catch (InterruptedException e) {
            log.error("Refund processing interrupted", e);
            return false;
        }
    }
}
