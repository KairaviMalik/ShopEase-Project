package shopease.service;

import shopease.dao.*;
import shopease.model.*;

import java.util.List;

// ── OOP: Service Layer — business logic, uses DAOs ──
public class ShopService {

    // ── OOP: Composition — service HAS-A dao ──
    private final UserDAO    userDAO;
    private final ProductDAO productDAO;
    private final OrderDAO   orderDAO;
    private final ReturnDAO  returnDAO;

    public ShopService() {
        this.userDAO    = new UserDAOImpl();
        this.productDAO = new ProductDAOImpl();
        this.orderDAO   = new OrderDAOImpl();
        this.returnDAO  = new ReturnDAOImpl();
    }

    // ── AUTH ──
    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public void registerCustomer(String name, String email, String password,
                                  String phone, String city) {
        // Check if email already exists
        Customer existing = null;
        for (Customer c : userDAO.getAllCustomers()) {
            if (c.getEmail().equalsIgnoreCase(email)) { existing = c; break; }
        }
        if (existing != null) {
            System.out.println("❌ Email already registered!");
            return;
        }
        Customer c = new Customer();
        c.setName(name); c.setEmail(email); c.setPassword(password);
        c.setPhone(phone); c.setCity(city);
        userDAO.registerCustomer(c);
        System.out.println("\n📧 Welcome Email Sent to: " + email);
        System.out.println("   Subject: Welcome to ShopEase!");
        System.out.println("   Dear " + name + ", your account has been created successfully.");
        System.out.println("   Login with: " + email);
    }

    // ── PRODUCTS ──
    public List<Product> getAllProducts()                        { return productDAO.getAllProducts(); }
    public List<Product> getProductsByCategory(String category) { return productDAO.getProductsByCategory(category); }
    public Product       getProductById(int id)                 { return productDAO.getProductById(id); }
    public void          addProduct(Product p)                  { productDAO.addProduct(p); }

    // ── ORDERS ──
    public void placeOrder(Customer customer, int productId, int quantity, String payment) {
        Product p = productDAO.getProductById(productId);
        if (p == null)              { System.out.println("❌ Product not found!");       return; }
        if (p.getStock() < quantity){ System.out.println("❌ Insufficient stock!");      return; }

        double total = p.getPrice() * quantity;
        Order o = new Order(0, customer.getId(), customer.getName(),
                            productId, p.getName(), quantity, total, payment, "Confirmed", "");
        orderDAO.placeOrder(o);
        productDAO.updateStock(productId, p.getStock() - quantity);
        userDAO.updateCustomerStats(customer.getId(),
                customer.getTotalOrders() + 1, customer.getTotalReturns());
        customer.setTotalOrders(customer.getTotalOrders() + 1);
        System.out.println("\n📧 Order Confirmation Email Sent to: " + customer.getEmail());
        System.out.println("   " + p.getName() + " x" + quantity + " = Rs." + String.format("%.0f", total));
    }

    public List<Order> getMyOrders(int customerId)  { return orderDAO.getOrdersByCustomer(customerId); }
    public List<Order> getAllOrders()                { return orderDAO.getAllOrders(); }
    public double      getTotalRevenue()             { return orderDAO.getTotalRevenue(); }

    public void cancelOrder(int orderId) {
        orderDAO.updateOrderStatus(orderId, "Cancelled");
        System.out.println("✅ Order #" + orderId + " cancelled.");
    }

    // ── RETURNS ──
    public void submitReturn(Customer customer, int orderId, String productName, String reason) {
        ReturnRequest r = new ReturnRequest(0, orderId, customer.getId(),
                customer.getName(), productName, reason, "Pending", "");
        returnDAO.submitReturn(r);
        userDAO.updateCustomerStats(customer.getId(),
                customer.getTotalOrders(), customer.getTotalReturns() + 1);
        customer.setTotalReturns(customer.getTotalReturns() + 1);
    }

    public List<ReturnRequest> getAllReturns()                      { return returnDAO.getAllReturns(); }
    public List<ReturnRequest> getMyReturns(int customerId)        { return returnDAO.getReturnsByCustomer(customerId); }
    public void                approveReturn(int returnId)         { returnDAO.updateReturnStatus(returnId, "Approved"); }
    public void                rejectReturn(int returnId)          { returnDAO.updateReturnStatus(returnId, "Rejected"); }

    // ── ADMIN: Customer Management ──
    public List<Customer> getAllCustomers()                         { return userDAO.getAllCustomers(); }
    public void           blockCustomer(int id, boolean blocked)   { userDAO.blockCustomer(id, blocked); }
    public void           updateRisk(int id, String risk)          { userDAO.updateCustomerRisk(id, risk); }
}
