package cyclechronicles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class ShopAcceptTest {

    // Prüft, dass ein normaler Rennrad-Auftrag angenommen wird.
    @Test
    void acceptsRaceBikeWhenNoPendingOrders() {
        Shop shop = new Shop();
        Order order = order(Type.RACE, "Alice");

        assertTrue(shop.accept(order));
    }

    // Prüft, dass ein Single-Speed-Auftrag angenommen wird.
    @Test
    void acceptsSingleSpeedBikeWhenNoPendingOrders() {
        Shop shop = new Shop();
        Order order = order(Type.SINGLE_SPEED, "Alice");

        assertTrue(shop.accept(order));
    }

    // Prüft, dass ein Fixie-Auftrag angenommen wird.
    @Test
    void acceptsFixieBikeWhenNoPendingOrders() {
        Shop shop = new Shop();
        Order order = order(Type.FIXIE, "Alice");

        assertTrue(shop.accept(order));
    }

    // Prüft, dass Gravel-Bikes abgelehnt werden.
    @Test
    void rejectsGravelBike() {
        Shop shop = new Shop();
        Order order = order(Type.GRAVEL, "Alice");

        assertFalse(shop.accept(order));
    }

    // Prüft, dass E-Bikes abgelehnt werden.
    @Test
    void rejectsEBike() {
        Shop shop = new Shop();
        Order order = order(Type.EBIKE, "Alice");

        assertFalse(shop.accept(order));
    }

    // Prüft, dass ein Kunde nicht zwei offene Aufträge gleichzeitig haben darf.
    @Test
    void rejectsSecondPendingOrderFromSameCustomer() {
        Shop shop = new Shop();

        Order firstOrder = order(Type.RACE, "Alice");
        Order secondOrder = order(Type.RACE, "Alice");

        assertTrue(shop.accept(firstOrder));
        assertFalse(shop.accept(secondOrder));
    }

    // Prüft den Grenzwert: Bei vier offenen Aufträgen darf noch ein Auftrag angenommen werden.
    @Test
    void acceptsOrderWhenFourOtherOrdersArePending() {
        Shop shop = new Shop();

        fillShopWithOrders(shop, 4);

        Order newOrder = order(Type.RACE, "Alice");

        assertTrue(shop.accept(newOrder));
    }

    // Prüft den Grenzwert: Bei fünf offenen Aufträgen muss ein weiterer Auftrag abgelehnt werden.
    @Test
    void rejectsOrderWhenFiveOtherOrdersArePending() {
        Shop shop = new Shop();

        fillShopWithOrders(shop, 5);

        Order newOrder = order(Type.RACE, "Alice");

        assertFalse(shop.accept(newOrder));
    }

    // Erstellt ein Mockito-Mock-Objekt für Order.
    private Order order(Type type, String customer) {
        Order order = mock(Order.class);

        when(order.getBicycleType()).thenReturn(type);
        when(order.getCustomer()).thenReturn(customer);

        return order;
    }

    // Fügt mehrere gültige Aufträge mit unterschiedlichen Kunden hinzu.
    private void fillShopWithOrders(Shop shop, int numberOfOrders) {
        for (int i = 0; i < numberOfOrders; i++) {
            Order order = order(Type.RACE, "Customer " + i);

            assertTrue(shop.accept(order));
        }
    }
}
