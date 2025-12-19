package com.cinema.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cinema.entity.Booking;

// FACTORY PATTERN
@Component
public class PaymentFactory {

    private static final Logger log = LoggerFactory.getLogger(PaymentFactory.class);

    public PaymentStrategy createPaymentStrategy(Booking.PaymentMethod paymentMethod) {
        log.info("Creating payment strategy for method: {}", paymentMethod);

        return switch (paymentMethod) {
            case VISA -> {
                log.debug("Creating VisaPaymentStrategy");
                yield new VisaPaymentStrategy();
            }
            case CASH -> {
                log.debug("Creating CashPaymentStrategy");
                yield new CashPaymentStrategy();
            }
            case NET_BANKING -> {
                log.debug("Creating NetBankingPaymentStrategy");
                yield new NetBankingPaymentStrategy();
            }
            default -> {
                log.warn("Unknown payment method: {}, defaulting to Cash", paymentMethod);
                yield new CashPaymentStrategy();
            }
        };
    }

    public PaymentStrategy createPaymentStrategy(String paymentMethodName) {
        try {
            Booking.PaymentMethod method = Booking.PaymentMethod.valueOf(paymentMethodName.toUpperCase());
            return createPaymentStrategy(method);
        } catch (IllegalArgumentException e) {
            log.error("Invalid payment method name: {}", paymentMethodName);
            return new CashPaymentStrategy();
        }
    }
}
