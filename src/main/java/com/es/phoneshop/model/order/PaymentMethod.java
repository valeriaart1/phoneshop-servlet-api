package com.es.phoneshop.model.order;

public enum PaymentMethod {
    CREDIT_CARD, CASH;

    public static void setPaymentMethod(String stringPaymentMethod, Order order) {
        order.setPaymentMethod(PaymentMethod.valueOf(stringPaymentMethod));
    }
}
