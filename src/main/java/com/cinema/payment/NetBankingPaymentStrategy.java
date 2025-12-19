package com.cinema.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// STRATEGY PATTERN
public class NetBankingPaymentStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(NetBankingPaymentStrategy.class);

    @Override
    public boolean processPayment(Double amount, String transactionDetails) {
        log.info("Processing Net Banking payment of amount: {}", amount);
        
        if (!validatePaymentDetails(transactionDetails)) {
            log.error("Invalid net banking credentials");
            return false;
        }

        try {
            log.info("Redirecting to bank portal...");
            Thread.sleep(800);
            
            log.info("Net banking payment processed successfully for amount: {}", amount);
            return true;
        } catch (InterruptedException e) {
            log.error("Net banking payment interrupted", e);
            return false;
        }
    }

    @Override
    public boolean validatePaymentDetails(String credentials) {
        log.info("Validating net banking credentials");
        return credentials != null && credentials.contains("@");
    }

    @Override
    public String getPaymentMethodName() {
        return "NET_BANKING";
    }

    @Override
    public boolean refundPayment(Double amount, String transactionId) {
        log.info("Processing Net Banking refund of amount: {} with transaction ID: {}", amount, transactionId);
        try {
            Thread.sleep(600);
            log.info("Net Banking refund processed successfully");
            return true;
        } catch (InterruptedException e) {
            log.error("Refund processing interrupted", e);
            return false;
        }
    }
}
