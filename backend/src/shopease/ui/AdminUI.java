package shopease.ui;

import shopease.model.*;
import shopease.service.ShopService;

import java.util.List;
import java.util.Scanner;

// ── OOP: Encapsulation — admin-specific UI ──
public class AdminUI {

    private final ShopService service;
    private final Admin       admin;
    private final Scanner     sc;

    public AdminUI(ShopService service, Admin admin, Scanner sc) {
        this.service = service;
        this.admin   = admin;
        this.sc      = sc;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║   🛡  Admin Dashboard             ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. View All Customers           ║");
            System.out.println("║  2. View All Orders + Revenue    ║");
            System.out.println("║  3. View Return Requests         ║");
            System.out.println("║  4. Approve / Reject Return      ║");
            System.out.println("║  5. Block / Unblock Customer     ║");
            System.out.println("║  6. Update Customer Risk Level   ║");
            System.out.println("║  7. View All Products            ║");
            System.out.println("║  8. Add New Product              ║");
            System.out.println("║  0. Logout                       ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Choice: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> viewAllCustomers();
                case 2 -> viewOrdersAndRevenue();
                case 3 -> viewReturnRequests();
                case 4 -> handleReturn();
                case 5 -> blockUnblockCustomer();
                case 6 -> updateRisk();
                case 7 -> viewAllProducts();
                case 8 -> addProduct();
                case 0 -> running = false;
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    // ── 1. All Customers ──
    private void viewAllCustomers() {
        List<Customer> customers = service.getAllCustomers();
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-4s %-18s %-25s %-8s %-7s %-7s %-8s %-10s │%n",
                "ID","Name","Email","Risk","Orders","Returns","Blocked","Joined");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────┤");
        for (Customer c : customers) {
            System.out.printf("│ %-4d %-18s %-25s %-8s %-7d %-7d %-8s %-10s │%n",
                    c.getId(), c.getName(), c.getEmail(),
                    c.getRiskLevel(), c.getTotalOrders(), c.getTotalReturns(),
                    c.isBlocked() ? "YES" : "no",
                    c.getJoinedDate() != null ? c.getJoinedDate() : "-");
        }
        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("  Total customers: " + customers.size());
    }

    // ── 2. Orders + Revenue ──
    private void viewOrdersAndRevenue() {
        List<Order> orders = service.getAllOrders();
        double revenue = service.getTotalRevenue();

        System.out.println("\n── All Orders ────────────────────────────────────────────────────────────────────");
        if (orders.isEmpty()) { System.out.println("  No orders yet."); }
        else {
            System.out.printf("  %-6s %-18s %-25s %-4s %-10s %-16s %-10s %s%n",
                    "ID","Customer","Product","Qty","Amount","Payment","Status","Date");
            System.out.println("  " + "─".repeat(100));
            for (Order o : orders)
                System.out.printf("  #%-5d %-18s %-25s %-4d Rs.%-7.0f %-16s %-10s %s%n",
                        o.getId(), o.getCustomerName(), o.getProductName(),
                        o.getQuantity(), o.getTotalAmount(),
                        o.getPaymentMethod(), o.getStatus(), o.getOrderDate());
        }
        System.out.printf("%n  📊 Total Orders   : %d%n", orders.size());
        System.out.printf("  💰 Total Revenue  : Rs.%.0f (Confirmed orders only)%n", revenue);
        long confirmed = orders.stream().filter(o -> "Confirmed".equals(o.getStatus())).count();
        long cancelled = orders.stream().filter(o -> "Cancelled".equals(o.getStatus())).count();
        System.out.printf("  ✅ Confirmed: %d  ❌ Cancelled: %d%n", confirmed, cancelled);
    }

    // ── 3. Return Requests ──
    private void viewReturnRequests() {
        List<ReturnRequest> returns = service.getAllReturns();
        System.out.println("\n── Return Requests ───────────────────────────────────────────────────────────────");
        if (returns.isEmpty()) { System.out.println("  No return requests yet."); return; }
        System.out.printf("  %-6s %-7s %-18s %-25s %-22s %-10s %s%n",
                "ID","Order#","Customer","Product","Reason","Status","Date");
        System.out.println("  " + "─".repeat(100));
        for (ReturnRequest r : returns)
            System.out.printf("  #%-5d #%-6d %-18s %-25s %-22s %-10s %s%n",
                    r.getId(), r.getOrderId(), r.getCustomerName(),
                    r.getProductName(), r.getReason(), r.getStatus(), r.getRequestDate());
        long pending  = returns.stream().filter(r -> "Pending".equals(r.getStatus())).count();
        long approved = returns.stream().filter(r -> "Approved".equals(r.getStatus())).count();
        long rejected = returns.stream().filter(r -> "Rejected".equals(r.getStatus())).count();
        System.out.printf("%n  Pending:%d  Approved:%d  Rejected:%d%n", pending, approved, rejected);
    }

    // ── 4. Approve / Reject Return ──
    private void handleReturn() {
        viewReturnRequests();
        List<ReturnRequest> pending = service.getAllReturns().stream()
                .filter(r -> "Pending".equals(r.getStatus())).toList();
        if (pending.isEmpty()) { System.out.println("  No pending returns."); return; }
        System.out.print("\nEnter Return ID: ");
        int rid = readInt();
        System.out.print("Action: 1.Approve  2.Reject: ");
        int action = readInt();
        if (action == 1)      service.approveReturn(rid);
        else if (action == 2) service.rejectReturn(rid);
        else System.out.println("❌ Invalid.");
    }

    // ── 5. Block / Unblock ──
    private void blockUnblockCustomer() {
        viewAllCustomers();
        System.out.print("\nEnter Customer ID: ");
        int cid = readInt();
        System.out.print("1.Block  2.Unblock: ");
        int action = readInt();
        if (action == 1)      service.blockCustomer(cid, true);
        else if (action == 2) service.blockCustomer(cid, false);
        else System.out.println("❌ Invalid.");
    }

    // ── 6. Update Risk ──
    private void updateRisk() {
        viewAllCustomers();
        System.out.print("\nEnter Customer ID: ");
        int cid = readInt();
        System.out.print("Risk Level (low/medium/high): ");
        String risk = sc.nextLine().trim().toLowerCase();
        if (!risk.equals("low") && !risk.equals("medium") && !risk.equals("high")) {
            System.out.println("❌ Invalid. Use low/medium/high"); return;
        }
        service.updateRisk(cid, risk);
        System.out.println("✅ Risk updated to: " + risk);
    }

    // ── 7. View Products ──
    private void viewAllProducts() {
        List<Product> products = service.getAllProducts();
        System.out.println("\n── Products ──────────────────────────────────────────────────────────────────────");
        System.out.printf("  %-4s %-30s %-12s %-6s %-12s %-6s%n","ID","Name","Price","Stock","Category","Rating");
        System.out.println("  " + "─".repeat(80));
        for (Product p : products)
            System.out.printf("  %-4d %-30s Rs.%-9.0f %-6d %-12s ★%.1f%n",
                    p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getCategory(), p.getRating());
        System.out.println("  Total: " + products.size() + " products");
    }

    // ── 8. Add Product ──
    private void addProduct() {
        System.out.print("Product Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Price (Rs.): ");
        double price = readDouble();
        System.out.print("Stock quantity: ");
        int stock = readInt();
        System.out.println("Category: Electronics / Clothing / Books / Home / Sports / Toys");
        System.out.print("Category: ");
        String cat = sc.nextLine().trim();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();
        Product p = new Product(0, name, price, stock, cat, desc, 4.0);
        service.addProduct(p);
    }

    private int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private double readDouble() {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return 0; }
    }
}
