package shopease.dao;

import shopease.model.Product;
import java.util.List;

// ── OOP: Interface ──
public interface ProductDAO {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    Product       getProductById(int id);
    void          updateStock(int productId, int newStock);
    void          addProduct(Product product);
}
