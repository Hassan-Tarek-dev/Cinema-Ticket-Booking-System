package com.cinema.payment;

import com.cinema.entity.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// STRATEGY PATTERN
public class PaymentProcessor {

    private static final Logger log = LoggerFactory.getLogger(PaymentProcessor.class);

    private PaymentStrategy paymentStrategy;

    public PaymentProcessor(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean pay(Double amount, String transactionDetails) {
        if (paymentStrategy == null) {
            log.error("Payment strategy not set");
            return false;
        }

        log.info("Processing payment using strategy: {}", paymentStrategy.getPaymentMethodName());
        return paymentStrategy.processPayment(amount, transactionDetails);
    }

    public String getPaymentMethod() {
        return paymentStrategy.getPaymentMethodName();
    }

    public static PaymentStrategy getStrategy(Booking.PaymentMethod method) {
        switch (method) {
            case VISA:
            case DEBIT_CARD:
                return new VisaPaymentStrategy();
            case CASH:
                return new CashPaymentStrategy();
            case NET_BANKING:
                return new NetBankingPaymentStrategy();
            default:
                log.warn("Unknown payment method: {}", method);
                return new CashPaymentStrategy();
        }
    }
}
