package shopease.dao;

import shopease.model.ReturnRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ── OOP: Implements ReturnDAO ──
public class ReturnDAOImpl implements ReturnDAO {

    private Connection conn;

    public ReturnDAOImpl() {
        this.conn = DBConnection.getConnection();
    }

    @Override
    public void submitReturn(ReturnRequest r) {
        String sql = "INSERT INTO return_requests (order_id, customer_id, customer_name, product_name, reason, status, request_date) " +
                     "VALUES (?,?,?,?,?,'Pending',CURDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getOrderId());
            ps.setInt(2, r.getCustomerId());
            ps.setString(3, r.getCustomerName());
            ps.setString(4, r.getProductName());
            ps.setString(5, r.getReason());
            ps.executeUpdate();
            System.out.println("✅ Return request submitted.");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    @Override
    public List<ReturnRequest> getAllReturns() {
        List<ReturnRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM return_requests ORDER BY request_date DESC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ReturnRequest> getReturnsByCustomer(int customerId) {
        List<ReturnRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM return_requests WHERE customer_id=? ORDER BY request_date DESC";
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
    public void updateReturnStatus(int returnId, String status) {
        String sql = "UPDATE return_requests SET status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, returnId);
            ps.executeUpdate();
            System.out.println("✅ Return #" + returnId + " marked as " + status);
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private ReturnRequest mapRow(ResultSet rs) throws SQLException {
        return new ReturnRequest(
            rs.getInt("id"),
            rs.getInt("order_id"),
            rs.getInt("customer_id"),
            rs.getString("customer_name"),
            rs.getString("product_name"),
            rs.getString("reason"),
            rs.getString("status"),
            rs.getString("request_date")
        );
    }
}
