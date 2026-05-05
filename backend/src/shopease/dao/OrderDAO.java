package shopease.dao;

import shopease.model.Order;
import java.util.List;

// ── OOP: Interface ──
public interface OrderDAO {
    void        placeOrder(Order order);
    List<Order> getOrdersByCustomer(int customerId);
    List<Order> getAllOrders();
    void        updateOrderStatus(int orderId, String status);
    double      getTotalRevenue();
}
