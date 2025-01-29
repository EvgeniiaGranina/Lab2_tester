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


}
