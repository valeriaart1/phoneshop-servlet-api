package com.es.phoneshop.model.order;

public enum DeliveryMode {
    COURIER, STORE_PICKUP;

    public static void setDeliveryMode(String stringDeliveryMode, Order order) {
        order.setDeliveryMode(DeliveryMode.valueOf(stringDeliveryMode));
    }
}
