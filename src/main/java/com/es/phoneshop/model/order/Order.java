package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order extends Cart {
    private Long id;
    private String secureId;
    private String firstName;
    private String lastName;
    private String phone;
    private DeliveryMode deliveryMode;
    private LocalDate deliveryDate;
    private BigDecimal deliveryCost;
    private String deliveryAddress;
    private BigDecimal orderCost;
    private PaymentMethod paymentMethod;

    public Order() {
    }

    public Order(List<CartItem> cartItems, BigDecimal totalCost, int totalQuantity, String firstName,
                 String lastName, String phone, DeliveryMode deliveryMode, LocalDate deliveryDate,
                 BigDecimal deliveryCost, String deliveryAddress, BigDecimal orderCost, PaymentMethod paymentMethod) {
        super(cartItems, totalCost, totalQuantity);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.deliveryMode = deliveryMode;
        this.deliveryDate = deliveryDate;
        this.deliveryCost = deliveryCost;
        this.deliveryAddress = deliveryAddress;
        this.orderCost = orderCost;
        this.paymentMethod = paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
