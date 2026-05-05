package service;

import model.Product;
import java.util.*;

// SERVICE: Manages product inventory (Admin operations)
public class ProductService {
    private Map<Integer, Product> inventory = new HashMap<>();

    // Add product to inventory
    public void addProduct(Product p) {
        inventory.put(p.getProductId(), p);
        System.out.println("  ✅ Product added: " + p.getName());
    }

    // Remove product
    public boolean removeProduct(int productId) {
        if (inventory.containsKey(productId)) {
            System.out.println("  ✅ Removed: " + inventory.get(productId).getName());
            inventory.remove(productId);
            return true;
        }
        System.out.println("  ❌ Product ID not found: " + productId);
        return false;
    }

    // Update stock
    public void updateStock(int productId, int newStock) {
        Product p = inventory.get(productId);
        if (p != null) {
            p.setStock(newStock);
            System.out.printf("  ✅ Stock updated: %s -> %d units%n", p.getName(), newStock);
        } else {
            System.out.println("  ❌ Product not found.");
        }
    }

    // Update price
    public void updatePrice(int productId, double newPrice) {
        Product p = inventory.get(productId);
        if (p != null) {
            p.setPrice(newPrice);
            System.out.printf("  ✅ Price updated: %s -> Rs. %.2f%n", p.getName(), newPrice);
        }
    }

    // Get product by ID
    public Product getProductById(int id) {
        return inventory.get(id);
    }

    // Search by name (case-insensitive)
    public List<Product> searchByName(String keyword) {
        List<Product> results = new ArrayList<>();
        for (Product p : inventory.values()) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    // Get by category
    public List<Product> getByCategory(String category) {
        List<Product> results = new ArrayList<>();
        for (Product p : inventory.values()) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                results.add(p);
            }
        }
        return results;
    }

    // Display all products
    public void displayAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("  No products in inventory.");
            return;
        }
        System.out.println("\n" + "=".repeat(65));
        System.out.println("                  📦  PRODUCT CATALOGUE");
        System.out.println("=".repeat(65));
        for (Product p : inventory.values()) {
            System.out.println("  " + p);
            System.out.println("     └─ " + p.getDescription());
        }
        System.out.println("=".repeat(65));
    }

    public Map<Integer, Product> getInventory() { return inventory; }
}