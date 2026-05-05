package shopease.dao;

import shopease.model.Admin;
import shopease.model.Customer;
import shopease.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ── OOP: Implements UserDAO interface (Abstraction + Polymorphism) ──
public class UserDAOImpl implements UserDAO {

    private Connection conn;

    public UserDAOImpl() {
        this.conn = DBConnection.getConnection();
    }

    // ── Register new customer ──
    @Override
    public void registerCustomer(Customer c) {
        String sql = "INSERT INTO users (name, email, password, phone, city, role, risk_level, total_orders, total_returns, blocked, joined_date) " +
                     "VALUES (?, ?, ?, ?, ?, 'Customer', 'low', 0, 0, false, CURDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPassword());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getCity());
            ps.executeUpdate();
            System.out.println("✅ Customer registered: " + c.getName());
        } catch (SQLException e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
        }
    }

    // ── Login — returns User (Admin or Customer) ──
    @Override
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if ("Admin".equals(role)) {
                    return new Admin(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("phone"), rs.getString("city")
                    );
                } else {
                    Customer c = new Customer(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("phone"), rs.getString("city"),
                        rs.getString("risk_level"),
                        rs.getInt("total_orders"),
                        rs.getInt("total_returns"),
                        rs.getBoolean("blocked"),
                        rs.getString("joined_date")
                    );
                    if (c.isBlocked()) {
                        System.out.println("❌ Your account is blocked. Contact support.");
                        return null;
                    }
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Login error: " + e.getMessage());
        }
        return null;
    }

    // ── Get single customer ──
    @Override
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM users WHERE id=? AND role='Customer'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("id"), rs.getString("name"),
                    rs.getString("email"), rs.getString("password"),
                    rs.getString("phone"), rs.getString("city"),
                    rs.getString("risk_level"),
                    rs.getInt("total_orders"), rs.getInt("total_returns"),
                    rs.getBoolean("blocked"), rs.getString("joined_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

    // ── Get all customers (Admin view) ──
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role='Customer' ORDER BY id";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(
                    rs.getInt("id"), rs.getString("name"),
                    rs.getString("email"), rs.getString("password"),
                    rs.getString("phone"), rs.getString("city"),
                    rs.getString("risk_level"),
                    rs.getInt("total_orders"), rs.getInt("total_returns"),
                    rs.getBoolean("blocked"), rs.getString("joined_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching customers: " + e.getMessage());
        }
        return list;
    }

    // ── Update risk level ──
    @Override
    public void updateCustomerRisk(int customerId, String riskLevel) {
        String sql = "UPDATE users SET risk_level=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, riskLevel);
            ps.setInt(2, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error updating risk: " + e.getMessage());
        }
    }

    // ── Block / Unblock customer ──
    @Override
    public void blockCustomer(int customerId, boolean blocked) {
        String sql = "UPDATE users SET blocked=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, blocked);
            ps.setInt(2, customerId);
            ps.executeUpdate();
            System.out.println("✅ Customer " + (blocked ? "blocked" : "unblocked"));
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ── Update order/return counts ──
    @Override
    public void updateCustomerStats(int customerId, int orders, int returns) {
        String sql = "UPDATE users SET total_orders=?, total_returns=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orders);
            ps.setInt(2, returns);
            ps.setInt(3, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error updating stats: " + e.getMessage());
        }
    }
}
