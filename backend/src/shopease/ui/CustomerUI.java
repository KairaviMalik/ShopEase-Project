package shopease.ui;

import shopease.model.*;
import shopease.service.ShopService;

import java.util.List;
import java.util.Scanner;

// ── OOP: Encapsulation — customer-specific UI ──
public class CustomerUI {

    private final ShopService service;
    private final Customer    customer;
    private final Scanner     sc;

    public CustomerUI(ShopService service, Customer customer, Scanner sc) {
        this.service  = service;
        this.customer = customer;
        this.sc       = sc;
    }

    public void show() {
        System.out.println("\n📧 Login Email Sent to: " + customer.getEmail());
        System.out.println("   Welcome back, " + customer.getName() + "!");

        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║   👤 " + customer.getName() + " (Customer)");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Browse Products          ║");
            System.out.println("║  2. Browse by Category       ║");
            System.out.println("║  3. Place Order              ║");
            System.out.println("║  4. My Orders                ║");
            System.out.println("║  5. Submit Return Request    ║");
            System.out.println("║  6. My Returns               ║");
            System.out.println("║  0. Logout                   ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Choice: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> browseProducts();
                case 2 -> browseByCategory();
                case 3 -> placeOrder();
                case 4 -> viewMyOrders();
                case 5 -> submitReturn();
                case 6 -> viewMyReturns();
                case 0 -> running = false;
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void browseProducts() {
        List<Product> products = service.getAllProducts();
        System.out.println("\n┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-4s %-30s %-12s %-6s %-12s %-6s │%n", "ID","Product","Price","Stock","Category","Rating");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        for (Product p : products)
            System.out.printf("│ %-4d %-30s Rs.%-9.0f %-6d %-12s ★%-5.1f │%n",
                    p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getCategory(), p.getRating());
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
    }

    private void browseByCategory() {
        System.out.println("\nCategories: Electronics / Clothing / Books / Home / Sports / Toys");
        System.out.print("Enter category: ");
        String cat = sc.nextLine().trim();
        List<Product> products = service.getProductsByCategory(cat);
        if (products.isEmpty()) { System.out.println("No products in this category."); return; }
        System.out.println("\n── " + cat + " ──────────────────────────");
        for (Product p : products)
            System.out.printf("  [%d] %-30s Rs.%-8.0f Stock:%-4d ★%.1f%n",
                    p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getRating());
    }

    private void placeOrder() {
        browseProducts();
        System.out.print("Enter Product ID: ");
        int pid = readInt();
        System.out.print("Quantity: ");
        int qty = readInt();
        System.out.println("Payment: 1.UPI  2.Cash on Delivery  3.Credit Card");
        System.out.print("Choice: ");
        int payChoice = readInt();
        String payment = switch (payChoice) {
            case 1 -> "UPI";
            case 2 -> "Cash on Delivery";
            case 3 -> "Credit Card";
            default -> "UPI";
        };
        service.placeOrder(customer, pid, qty, payment);
    }

    private void viewMyOrders() {
        List<Order> orders = service.getMyOrders(customer.getId());
        if (orders.isEmpty()) { System.out.println("\n📦 No orders yet."); return; }
        System.out.println("\n── My Orders ─────────────────────────────────────────────────────");
        for (Order o : orders)
            System.out.printf("  #%-4d %-25s Qty:%-2d Rs.%-8.0f %-15s %-10s %s%n",
                    o.getId(), o.getProductName(), o.getQuantity(),
                    o.getTotalAmount(), o.getPaymentMethod(), o.getStatus(), o.getOrderDate());
    }

    private void submitReturn() {
        List<Order> orders = service.getMyOrders(customer.getId());
        List<Order> confirmed = orders.stream()
                .filter(o -> "Confirmed".equals(o.getStatus())).toList();
        if (confirmed.isEmpty()) { System.out.println("No eligible orders for return."); return; }

        System.out.println("\n── Eligible Orders for Return ─────");
        for (Order o : confirmed)
            System.out.printf("  #%-4d %-25s Rs.%.0f  %s%n",
                    o.getId(), o.getProductName(), o.getTotalAmount(), o.getOrderDate());

        System.out.print("Enter Order ID to return: ");
        int oid = readInt();
        Order order = confirmed.stream().filter(o -> o.getId() == oid).findFirst().orElse(null);
        if (order == null) { System.out.println("❌ Invalid order ID."); return; }

        System.out.println("Reason: 1.Defective  2.Wrong Item  3.Not Needed  4.Other");
        System.out.print("Choice: ");
        int r = readInt();
        String reason = switch (r) {
            case 1 -> "Defective Product";
            case 2 -> "Wrong Item Delivered";
            case 3 -> "Not Needed";
            default -> "Other";
        };
        service.submitReturn(customer, oid, order.getProductName(), reason);
    }

    private void viewMyReturns() {
        List<ReturnRequest> returns = service.getMyReturns(customer.getId());
        if (returns.isEmpty()) { System.out.println("\nNo return requests."); return; }
        System.out.println("\n── My Return Requests ─────────────────────────────────────────");
        for (ReturnRequest r : returns)
            System.out.printf("  Return#%-4d | Order#%-4d | %-20s | %-20s | %s | %s%n",
                    r.getId(), r.getOrderId(), r.getProductName(),
                    r.getReason(), r.getStatus(), r.getRequestDate());
    }

    private int readInt() {
        try { int v = Integer.parseInt(sc.nextLine().trim()); return v; }
        catch (NumberFormatException e) { return -1; }
    }
}
