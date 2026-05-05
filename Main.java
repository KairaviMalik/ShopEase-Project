import model.*;
import payment.*;
import service.*;

/**
 * ============================================================
 *   ONLINE SHOPPING SYSTEM — Main Driver / Demo
 *   Demonstrates: Encapsulation, Inheritance, Polymorphism,
 *                 Abstraction, Interface, Service Layer
 * ============================================================
 */
public class Main {

    public static void main(String[] args) {

        banner();

        // ─────────────────────────────────────────────
        // 1. PRODUCT SERVICE SETUP (Admin adds products)
        // ─────────────────────────────────────────────
        ProductService productService = new ProductService();
        OrderService   orderService   = new OrderService();

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   ADMIN: Adding Products to Store    ║");
        System.out.println("╚══════════════════════════════════════╝");

        // POLYMORPHISM: different product subclasses stored as Product
        productService.addProduct(new Electronics(101, "Samsung Galaxy S23",  79999, 15, "Samsung", 12));
        productService.addProduct(new Electronics(102, "Apple MacBook Air",   99999, 8,  "Apple",   12));
        productService.addProduct(new Electronics(103, "Sony Bluetooth Earbuds", 3999, 30, "Sony", 6));
        productService.addProduct(new Clothing   (104, "Men's Casual T-Shirt", 599,  50, "L",     "Navy Blue"));
        productService.addProduct(new Clothing   (105, "Women's Kurti",        899,  40, "M",     "Red"));
        productService.addProduct(new Book       (106, "Java: OOP Mastery",    449,  100, "James Gosling", "978-0-13-468599-1"));
        productService.addProduct(new Book       (107, "Clean Code",           599,  75,  "Robert Martin", "978-0-13-235088-4"));

        // ─────────────────────────────────────────────
        // 2. BROWSE CATALOGUE
        // ─────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   CUSTOMER: Browsing Product Catalog ║");
        System.out.println("╚══════════════════════════════════════╝");
        productService.displayAllProducts();

        // ─────────────────────────────────────────────
        // 3. SEARCH & FILTER
        // ─────────────────────────────────────────────
        System.out.println("\n🔍 Search results for 'java':");
        for (Product p : productService.searchByName("java")) {
            System.out.println("   " + p);
        }

        System.out.println("\n📂 Category filter - Electronics:");
        for (Product p : productService.getByCategory("Electronics")) {
            System.out.println("   " + p);
        }

        // ─────────────────────────────────────────────
        // 4. CUSTOMER 1 — UPI PAYMENT
        // ─────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   CUSTOMER 1: Rahul — UPI Payment    ║");
        System.out.println("╚══════════════════════════════════════╝");

        Customer rahul = new Customer(1, "Rahul Sharma",
                "rahul@email.com", "pass123", "Mumbai, Maharashtra");

        CartService cartRahul = new CartService();
        cartRahul.addToCart(productService.getProductById(103), 2); // Sony Earbuds x2
        cartRahul.addToCart(productService.getProductById(106), 1); // Java Book x1
        cartRahul.addToCart(productService.getProductById(104), 3); // T-Shirt x3

        // POLYMORPHISM: Payment via UPI
        Payment upi = new UPIPayment("rahul@upi");
        orderService.placeOrder(rahul, cartRahul, upi);

        // ─────────────────────────────────────────────
        // 5. CUSTOMER 2 — CREDIT CARD PAYMENT
        // ─────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║   CUSTOMER 2: Priya — Credit Card        ║");
        System.out.println("╚══════════════════════════════════════════╝");

        Customer priya = new Customer(2, "Priya Patel",
                "priya@email.com", "priya456", "Pune, Maharashtra");

        CartService cartPriya = new CartService();
        cartPriya.addToCart(productService.getProductById(102), 1); // MacBook x1
        cartPriya.addToCart(productService.getProductById(107), 2); // Clean Code x2

        // POLYMORPHISM: Payment via Credit Card
        Payment card = new CreditCardPayment("4111111111111234", "Priya Patel");
        orderService.placeOrder(priya, cartPriya, card);

        // ─────────────────────────────────────────────
        // 6. CUSTOMER 3 — CASH ON DELIVERY
        // ─────────────────────────────────────────────
        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║   CUSTOMER 3: Amit — Cash on Delivery       ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        Customer amit = new Customer(3, "Amit Singh",
                "amit@email.com", "amit789", "Delhi, India");

        CartService cartAmit = new CartService();
        cartAmit.addToCart(productService.getProductById(101), 1); // Samsung phone
        cartAmit.addToCart(productService.getProductById(105), 2); // Kurti x2

        Payment cod = new CashOnDeliveryPayment(amit.getAddress());
        Order amitOrder = orderService.placeOrder(amit, cartAmit, cod);

        // ─────────────────────────────────────────────
        // 7. ORDER TRACKING + HISTORY
        // ─────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   ORDER TRACKING                     ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (amitOrder != null) {
            orderService.trackOrder(amitOrder.getOrderId());
        }

        System.out.println("\n📋 Rahul's Order History:");
        rahul.printOrderHistory();

        System.out.println("\n📋 Priya's Order History:");
        priya.printOrderHistory();

        // ─────────────────────────────────────────────
        // 8. ADMIN: STOCK UPDATE + PRICE CHANGE
        // ─────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   ADMIN: Inventory Management        ║");
        System.out.println("╚══════════════════════════════════════╝");
        productService.updateStock(106, 200);
        productService.updatePrice(104, 549);
        productService.removeProduct(999); // non-existent

        // ─────────────────────────────────────────────
        // 9. EDGE CASES
        // ─────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   EDGE CASES                         ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Empty cart checkout
        CartService emptyCart = new CartService();
        orderService.placeOrder(rahul, emptyCart, upi);

        // Exceed stock
        CartService bigCart = new CartService();
        bigCart.addToCart(productService.getProductById(102), 100); // MacBook: only 8 left

        // Remove item
        cartRahul.addToCart(productService.getProductById(107), 1);
        cartRahul.removeFromCart(107);
        cartRahul.removeFromCart(999);

        System.out.println("\n✅ Demo complete. All OOP concepts demonstrated!");
        System.out.println("   Encapsulation | Inheritance | Polymorphism | Abstraction");
        System.out.println("─".repeat(55));
    }

    private static void banner() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                                                       ║");
        System.out.println("║      🛒  ONLINE SHOPPING SYSTEM — Java OOP            ║");
        System.out.println("║      Encapsulation | Inheritance | Polymorphism       ║");
        System.out.println("║      Abstraction | Interface | Service Layer          ║");
        System.out.println("║                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }
}