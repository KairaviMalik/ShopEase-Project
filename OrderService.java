package service;

import model.*;
import payment.Payment;
import java.util.*;

// SERVICE: Order generation and management
public class OrderService {
    private List<Order> allOrders = new ArrayList<>();

    // Place an order
    public Order placeOrder(Customer customer, CartService cart, Payment payment) {
        if (cart.isEmpty()) {
            System.out.println("  ❌ Cannot place order. Cart is empty!");
            return null;
        }
        double total = cart.getTotal();
        System.out.println("\n  💰 Order Summary:");
        cart.viewCart();
        System.out.printf("%n  Processing payment of Rs. %.2f via %s...%n",
                total, payment.getPaymentMethod());
        System.out.println("─".repeat(55));

        boolean paymentSuccess = payment.processPayment(total);
        if (!paymentSuccess) {
            System.out.println("  ❌ Payment failed. Order not placed.");
            return null;
        }

        // Deduct stock
        for (CartItem item : cart.getCartItems()) {
            Product p = item.getProduct();
            p.setStock(p.getStock() - item.getQuantity());
        }

        Order order = new Order(customer.getName(),
                cart.getCartItems(), total, payment.getPaymentMethod());
        allOrders.add(order);
        customer.addOrder(order);
        cart.clearCart();

        order.printReceipt();
        return order;
    }

    // Cancel an order
    public void cancelOrder(int orderId) {
        for (Order o : allOrders) {
            if (o.getOrderId() == orderId) {
                if (o.getStatus().equals("Confirmed")) {
                    o.setStatus("Cancelled");
                    System.out.println("  ✅ Order #" + orderId + " cancelled.");
                } else {
                    System.out.println("  ❌ Cannot cancel. Status: " + o.getStatus());
                }
                return;
            }
        }
        System.out.println("  ❌ Order #" + orderId + " not found.");
    }

    // Track order
    public void trackOrder(int orderId) {
        for (Order o : allOrders) {
            if (o.getOrderId() == orderId) {
                System.out.printf("  Order #%d | Customer: %s | Status: %s%n",
                        o.getOrderId(), o.getCustomerName(), o.getStatus());
                return;
            }
        }
        System.out.println("  ❌ Order not found.");
    }

    public List<Order> getAllOrders() { return allOrders; }
}