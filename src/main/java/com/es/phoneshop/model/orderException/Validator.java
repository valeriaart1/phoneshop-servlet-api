package com.es.phoneshop.model.orderException;

import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static String validateFirstName(Order order, String firstName) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]+");
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
        Pattern pattern = Pattern.compile("[A-Z][a-z]+");
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

    public static Map<String, String> isOrderValid(Map<String, String> errors, Order order, HttpServletRequest request) {
        errors.put("firstNameError", Validator.validateFirstName(order, request.getParameter("firstName")));
        errors.put("lastNameError", Validator.validateLastName(order, request.getParameter("lastName")));
        errors.put("phoneError", Validator.validatePhone(order, request.getParameter("phone")));
        DeliveryMode.setDeliveryMode(request.getParameter("deliveryMode"), order);
        errors.put("deliveryAddressError", Validator.validateDeliveryAddress(order, request.getParameter("deliveryAddress")));
        PaymentMethod.setPaymentMethod(request.getParameter("paymentMethod"), order);
        return errors;
    }
}
