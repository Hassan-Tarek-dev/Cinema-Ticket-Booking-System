package com.cinema.pattern.factory;

import org.springframework.stereotype.Component;

// Interface
interface PaymentMethod {
    void pay(double amount);
}

// Concrete Implementations
class VisaPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Visa payment of $" + amount);
    }
}

class CashPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Cash payment of $" + amount);
    }
}

// Factory
@Component
public class PaymentFactory {
    
    public PaymentMethod getPaymentMethod(String type) {
        if ("VISA".equalsIgnoreCase(type)) {
            return new VisaPayment();
        } else if ("CASH".equalsIgnoreCase(type)) {
            return new CashPayment();
        }
        throw new IllegalArgumentException("Unknown payment type: " + type);
    }
}