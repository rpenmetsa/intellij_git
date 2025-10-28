package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple shopping cart for demonstration
 */
public class ShoppingCart {

    private List<String> items;
    private double totalPrice;
    private boolean checkedOut;

    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.checkedOut = false;
    }

    public void addItem(String item, double price) {
        if (checkedOut) {
            throw new IllegalStateException("Cannot add items after checkout");
        }
        items.add(item);
        totalPrice += price;
    }

    public void removeItem(String item, double price) {
        if (items.remove(item)) {
            totalPrice -= price;
        }
    }

    public int getItemCount() {
        return items.size();
    }

    public double getTotalPrice() {
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void checkout() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot checkout empty cart");
        }
        checkedOut = true;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void clear() {
        items.clear();
        totalPrice = 0.0;
        checkedOut = false;
    }
}
