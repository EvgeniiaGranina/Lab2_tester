package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    public void createShoppingCartObject() {
        cart = new ShoppingCart();
    }

    @Test
    public void testAddItem() {
        cart.addItem("", 1, 1.0);
        assertEquals(1, cart.getItemCount());
    }

    @Test
    public void testRemoveItem() {
        cart.addItem("Nötkött", 1, 100.0);
        cart.removeItem("Nötkött");
        assertEquals(0, cart.getItemCount());
    }

    @Test
    public void testCalculateTotalPrice() {
        cart.addItem("Mjölk", 2, 14.0);
        cart.addItem("Fanta", 1, 11.5);
        assertEquals(39.5, cart.calculateTotalPrice());
    }

    @Test
    public void testApplyDiscount() {
        cart.addItem("Falukorv", 2, 28.0);
        cart.applyDiscount(0.1); // 10% discount
        assertEquals(50.4, cart.calculateTotalPrice());
    }

    @Test
    public void testUpdateQuantity() {
        cart.addItem("Lime", 2, 5.0);
        cart.updateItemQuantity("Lime", 5);
        assertEquals(5, cart.getItemQuantity("Lime"));
    }

}
