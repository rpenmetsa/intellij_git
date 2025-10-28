package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Shopping Cart Tests using GIVEN-WHEN-THEN pattern
 *
 * GIVEN = Setup the initial state (arrange)
 * WHEN = Perform an action (act)
 * THEN = Verify the result (assert)
 */
@DisplayName("Shopping Cart Tests")
class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    @DisplayName("Adding item to empty cart increases item count")
    void test_AddItemToEmptyCart() {
        // GIVEN: An empty shopping cart
        assertTrue(cart.isEmpty(), "Cart should start empty");

        // WHEN: I add one item
        cart.addItem("Apple", 1.50);

        // THEN: The cart should have 1 item
        assertEquals(1, cart.getItemCount());
        assertFalse(cart.isEmpty());
    }

    @Test
    @DisplayName("Adding multiple items calculates correct total price")
    void test_AddMultipleItems_CalculatesTotal() {
        // GIVEN: An empty shopping cart
        // (cart is already empty from setUp)

        // WHEN: I add three items
        cart.addItem("Apple", 1.50);
        cart.addItem("Banana", 2.00);
        cart.addItem("Orange", 1.75);

        // THEN: Total price should be sum of all items
        assertEquals(5.25, cart.getTotalPrice());
        assertEquals(3, cart.getItemCount());
    }

    @Test
    @DisplayName("Removing item decreases count and updates price")
    void test_RemoveItem_UpdatesCartCorrectly() {
        // GIVEN: A cart with 2 items
        cart.addItem("Apple", 1.50);
        cart.addItem("Banana", 2.00);

        // WHEN: I remove one item
        cart.removeItem("Apple", 1.50);

        // THEN: Cart should have 1 item and reduced price
        assertEquals(1, cart.getItemCount());
        assertEquals(2.00, cart.getTotalPrice());
    }

    @Test
    @DisplayName("Checking out with items marks cart as checked out")
    void test_CheckoutWithItems_Success() {
        // GIVEN: A cart with items
        cart.addItem("Apple", 1.50);
        cart.addItem("Banana", 2.00);
        assertFalse(cart.isCheckedOut(), "Cart should not be checked out initially");

        // WHEN: I checkout
        cart.checkout();

        // THEN: Cart should be marked as checked out
        assertTrue(cart.isCheckedOut());
    }

    @Test
    @DisplayName("Checking out empty cart throws exception")
    void test_CheckoutEmptyCart_ThrowsException() {
        // GIVEN: An empty cart
        assertTrue(cart.isEmpty());

        // WHEN: I try to checkout
        // THEN: It should throw an exception
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            cart.checkout();
        });

        assertEquals("Cannot checkout empty cart", exception.getMessage());
    }

    @Test
    @DisplayName("Adding item after checkout throws exception")
    void test_AddItemAfterCheckout_ThrowsException() {
        // GIVEN: A checked out cart
        cart.addItem("Apple", 1.50);
        cart.checkout();
        assertTrue(cart.isCheckedOut());

        // WHEN: I try to add another item
        // THEN: It should throw an exception
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            cart.addItem("Banana", 2.00);
        });

        assertEquals("Cannot add items after checkout", exception.getMessage());
    }

    @Test
    @DisplayName("Clearing cart resets everything")
    void test_ClearCart_ResetsAllValues() {
        // GIVEN: A cart with items and checked out
        cart.addItem("Apple", 1.50);
        cart.addItem("Banana", 2.00);
        cart.checkout();

        // WHEN: I clear the cart
        cart.clear();

        // THEN: Everything should be reset
        assertAll("cart should be reset",
                () -> assertTrue(cart.isEmpty(), "Cart should be empty"),
                () -> assertEquals(0, cart.getItemCount(), "Item count should be 0"),
                () -> assertEquals(0.0, cart.getTotalPrice(), "Total price should be 0"),
                () -> assertFalse(cart.isCheckedOut(), "Should not be checked out")
        );
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("Removing non-existent item does nothing")
        void test_RemoveNonExistentItem() {
            // GIVEN: A cart with one item
            cart.addItem("Apple", 1.50);

            // WHEN: I try to remove an item that doesn't exist
            cart.removeItem("Banana", 2.00);

            // THEN: Cart should remain unchanged
            assertEquals(1, cart.getItemCount());
            assertEquals(1.50, cart.getTotalPrice());
        }

        @Test
        @DisplayName("Adding item with zero price")
        void test_AddItemWithZeroPrice() {
            // GIVEN: An empty cart

            // WHEN: I add a free item
            cart.addItem("Free Sample", 0.0);

            // THEN: Item is added but price stays zero
            assertEquals(1, cart.getItemCount());
            assertEquals(0.0, cart.getTotalPrice());
        }
    }

    @AfterEach
    void tearDown() {
        cart = null;
    }
}
