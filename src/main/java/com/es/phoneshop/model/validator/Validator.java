package com.es.phoneshop.model.validator;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.es.phoneshop.model.order.DeliveryMode.getDeliveryMode;
import static com.es.phoneshop.model.order.PaymentMethod.getPaymentMethod;

public class Validator {
    public static Map<String, String> validateOrder(Order order, String firstName, String lastName, String phone,
                                       String deliveryAddress) {
        Map<String, String> errors = new HashMap<>();

        Pattern patternFirstName = Pattern.compile("[A-Z][a-z]+([\\-][A-Z][a-z]+)*");
        Matcher matcherFirstName = patternFirstName.matcher(firstName);
        if (firstName == null || firstName.isEmpty()) {
            errors.put("firstNameError", "First name is required!");
        } else if (!matcherFirstName.matches()) {
            errors.put("firstNameError", "Error of first name! Example, Valeria");
        } else {
            order.setFirstName(firstName);
        }

        Pattern patternLastName = Pattern.compile("[A-Z][a-z]+([\\-][A-Z][a-z]+)*");
        Matcher matcherLastName = patternLastName.matcher(lastName);
        if (lastName == null || lastName.isEmpty()) {
            errors.put("lastNameError", "Last name is required!");
        } else if (!matcherLastName.matches()) {
            errors.put("lastNameError", "Error of last name! Example, Artemjeva");
        } else {
            order.setLastName(lastName);
        }

        Pattern patternPhone = Pattern.compile("\\+375(44|29|33|25)(\\d{7})");
        Matcher matcherPhone = patternPhone.matcher(phone);
        if (phone == null || phone.isEmpty()) {
            errors.put("phoneError", "Phone is required!");
        } else if (!matcherPhone.matches()) {
            errors.put("phoneError", "Error of phone! Example, +375449090909");
        } else {
            order.setPhone(phone);
        }

        Pattern patternAddress = Pattern.compile("[A-Z][a-z]+[\\,][\\s][A-Z][a-z]+[\\s]street[\\,][\\s][0-9]+[\\/]*[0-9]*");
        Matcher matcherAddress = patternAddress.matcher(deliveryAddress);
        if (deliveryAddress == null || deliveryAddress.isEmpty()) {
            errors.put("deliveryAddressError", "Delivery address is required!");
        } else if (!matcherAddress.matches()) {
            errors.put("deliveryAddressError", "Error of delivery address! Example, Minsk, Bedya street, 4");
        } else {
            order.setDeliveryAddress(deliveryAddress);
        }

        return errors;
    }

    public static Map<String, String> isOrderValid(Cart cart, Order order, String firstName,
                                                   String lastName, String phone, String deliveryMode,
                                                   String deliveryAddress, String paymentMethod) {
        Map<String, String> errors;
        errors = validateOrder(order, firstName, lastName, phone, deliveryAddress);
        order.setDeliveryMode(getDeliveryMode(deliveryMode));
        order.setPaymentMethod(getPaymentMethod(paymentMethod));
        order.setDeliveryCost(getDeliveryMode(deliveryMode).getDeliveryCost());
        order.setOrderCost(order.getDeliveryCost().add(cart.getTotalCost()));
        return errors;
    }

    public static Map<String, String> validateSearchingFields(String minPrice, String maxPrice,
                                                              String minStock, String maxStock) {
        Map<String, String> errors = new HashMap<>();
        String NOT_NUMBER = "Not a number";
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcherMinPrice = pattern.matcher(minPrice);
        Matcher matcherMaxPrice = pattern.matcher(maxPrice);
        Matcher matcherMinStock = pattern.matcher(minStock);
        Matcher matcherMaxStock = pattern.matcher(maxStock);

        if(!minPrice.isEmpty() && !matcherMinPrice.matches()) {
                errors.put("minPriceError", NOT_NUMBER);
        }
        if(!maxPrice.isEmpty() && !matcherMaxPrice.matches()) {
                errors.put("maxPriceError", NOT_NUMBER);
        }
        if(!minStock.isEmpty() && !matcherMinStock.matches()) {
                errors.put("minStockError", NOT_NUMBER);
        }
        if(!maxStock.isEmpty() && !matcherMaxStock.matches()) {
                errors.put("maxStockError", NOT_NUMBER);
        }
        
        if(errors.values().isEmpty()) {
            if(!minPrice.isEmpty() && !maxPrice.isEmpty()) {
                if (new BigDecimal(minPrice).compareTo(new BigDecimal(maxPrice)) >= 0) {
                    errors.put("maxPriceError", "Max price must be more than min price!");
                }
            }
            if(!minStock.isEmpty() && !maxStock.isEmpty()) {
                if (Integer.parseInt(minStock) >= Integer.parseInt(maxStock)) {
                    errors.put("maxStockError", "Max stock must be more than min stock!");
                }
            }
        }

        return errors;
    }
}
