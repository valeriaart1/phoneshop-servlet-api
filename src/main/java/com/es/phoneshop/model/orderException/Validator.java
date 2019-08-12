package com.es.phoneshop.model.orderException;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.es.phoneshop.model.order.DeliveryMode.getDeliveryMode;
import static com.es.phoneshop.model.order.PaymentMethod.getPaymentMethod;

public class Validator {
    public static String validateFirstName(Order order, String firstName) {
        Pattern pattern = Pattern.compile("([A-Z][a-z]+([\\-][A-Z][a-z]+)*)|([А-Я][а-я]+([\\-][А-Я][а-я]+)*)");
        Matcher matcher = pattern.matcher(firstName);

        if (firstName == null || firstName.isEmpty()) {
            return "First name is required!";
        }

        if (!matcher.matches()) {
            return "Error of first name! Example, Valeria.";
        }

        order.setFirstName(firstName);
        return null;
    }

    public static String validateLastName(Order order, String lastName) {
        Pattern pattern = Pattern.compile("([A-Z][a-z]+([\\-][A-Z][a-z]+)*)|([А-Я][а-я]+([\\-][А-Я][а-я]+)*)");
        Matcher matcher = pattern.matcher(lastName);

        if (lastName == null || lastName.isEmpty()) {
            return "Last name is required!";
        }

        if (!matcher.matches()) {
            return "Error of last name! Example, Artemjeva.";
        }

        order.setLastName(lastName);
        return null;
    }

    public static String validatePhone(Order order, String phone) {
        Pattern pattern = Pattern.compile("\\+375(44|29|33|25)(\\d{7})");
        Matcher matcher = pattern.matcher(phone);

        if (phone == null || phone.isEmpty()) {
            return "Phone is required!";
        }

        if (!matcher.matches()) {
            return "Error of phone! Example, +375449090909.";
        }

        order.setPhone(phone);
        return null;
    }

    public static String validateDeliveryAddress(Order order, String deliveryAddress) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]+[\\,][\\s][A-Z][a-z]+[\\s]street[\\,][\\s][0-9]+[\\/]*[0-9]*");
        Matcher matcher = pattern.matcher(deliveryAddress);

        if (deliveryAddress == null || deliveryAddress.isEmpty()) {
            return "Delivery address is required!";
        }

        if (!matcher.matches()) {
            return "Error of delivery address! Example, Minsk, Bedya street, 4/2.";
        }

        order.setDeliveryAddress(deliveryAddress);
        return null;
    }

    public static Map<String, String> isOrderValid(Map<String, String> errors, Cart cart, Order order, String firstName,
                                                   String lastName, String phone, String deliveryMode,
                                                   String deliveryAddress, String paymentMethod) {
        errors.put("firstNameError", Validator.validateFirstName(order, firstName));
        errors.put("lastNameError", Validator.validateLastName(order, lastName));
        errors.put("phoneError", Validator.validatePhone(order, phone));
        order.setDeliveryMode(getDeliveryMode(deliveryMode));
        errors.put("deliveryAddressError", Validator.validateDeliveryAddress(order, deliveryAddress));
        order.setPaymentMethod(getPaymentMethod(paymentMethod));
        order.setDeliveryCost(getDeliveryMode(deliveryMode).getDeliveryCost());
        order.setOrderCost(order.getDeliveryCost().add(cart.getTotalCost()));
        return errors;
    }
}
