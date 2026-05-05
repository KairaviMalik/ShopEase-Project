package shopease.dao;

import shopease.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ── OOP: Implements ProductDAO ──
public class ProductDAOImpl implements ProductDAO {

    private Connection conn;

    public ProductDAOImpl() {
        this.conn = DBConnection.getConnection();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY category, name";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=? ORDER BY name";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateStock(int productId, int newStock) {
        String sql = "UPDATE products SET stock=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error updating stock: " + e.getMessage());
        }
    }

    @Override
    public void addProduct(Product p) {
        String sql = "INSERT INTO products (name, price, stock, category, description, rating) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCategory());
            ps.setString(5, p.getDescription());
            ps.setDouble(6, p.getRating());
            ps.executeUpdate();
            System.out.println("✅ Product added: " + p.getName());
        } catch (SQLException e) {
            System.out.println("❌ Error adding product: " + e.getMessage());
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getDouble("price"),
            rs.getInt("stock"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getDouble("rating")
        );
    }
}
