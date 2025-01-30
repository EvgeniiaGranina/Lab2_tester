package com.example;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<String, Item> items;
    private double discount;

    public ShoppingCart() {
        items = new HashMap<>();
        discount = 0.0;
    }

    public void addItem(String name, int quantity, double price) {

        if (items.containsKey(name)) {
            Item item = items.get(name);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(name, new Item(name, quantity, price));
        }
    }

    public int getItemCount() {
        return items.size();
    }

    public void removeItem(String name) {
        items.remove(name);
    }

    public double calculateTotalPrice() {
        double total = 0.0;
        for (Item item : items.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total * (1 - discount);
    }

    public void applyDiscount(double discount) {
        this.discount = discount;
    }
}
