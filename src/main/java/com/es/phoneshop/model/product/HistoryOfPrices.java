package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class HistoryOfPrices implements Serializable {
    public String date;
    private BigDecimal price;
    private Currency currency;

    public HistoryOfPrices() {
    }

    public HistoryOfPrices(String string, BigDecimal bigDecimal, Currency usd) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}