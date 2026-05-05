package shopease.dao;

import shopease.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ── OOP: Implements OrderDAO ──
public class OrderDAOImpl implements OrderDAO {

    private Connection conn;

    public OrderDAOImpl() {
        this.conn = DBConnection.getConnection();
    }

    @Override
    public void placeOrder(Order o) {
        String sql = "INSERT INTO orders (customer_id, customer_name, product_id, product_name, quantity, total_amount, payment_method, status, order_date) " +
                     "VALUES (?,?,?,?,?,?,?,'Confirmed',CURDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getCustomerId());
            ps.setString(2, o.getCustomerName());
            ps.setInt(3, o.getProductId());
            ps.setString(4, o.getProductName());
            ps.setInt(5, o.getQuantity());
            ps.setDouble(6, o.getTotalAmount());
            ps.setString(7, o.getPaymentMethod());
            ps.executeUpdate();
            System.out.println("✅ Order placed successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Order failed: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id=? ORDER BY order_date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    @Override
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE status='Confirmed'";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return 0;
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        return new Order(
            rs.getInt("id"),
            rs.getInt("customer_id"),
            rs.getString("customer_name"),
            rs.getInt("product_id"),
            rs.getString("product_name"),
            rs.getInt("quantity"),
            rs.getDouble("total_amount"),
            rs.getString("payment_method"),
            rs.getString("status"),
            rs.getString("order_date")
        );
    }
}
