package service;

import model.CartItem;
import model.Product;
import java.util.*;

// SERVICE: Shopping cart operations (Customer)
public class CartService {
    private List<CartItem> cart = new ArrayList<>();

    // Add item to cart
    public void addToCart(Product product, int quantity) {
        if (!product.isAvailable()) {
            System.out.println("  ❌ Sorry, '" + product.getName() + "' is out of stock.");
            return;
        }
        if (quantity > product.getStock()) {
            System.out.printf("  ❌ Only %d units available for '%s'.%n",
                    product.getStock(), product.getName());
            return;
        }
        // If already in cart, increase quantity
        for (CartItem item : cart) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                int newQty = item.getQuantity() + quantity;
                if (newQty > product.getStock()) {
                    System.out.println("  ❌ Not enough stock for total quantity.");
                    return;
                }
                item.setQuantity(newQty);
                System.out.printf("  ✅ Updated '%s' quantity to %d in cart.%n",
                        product.getName(), newQty);
                return;
            }
        }
        cart.add(new CartItem(product, quantity));
        System.out.printf("  ✅ Added '%s' (x%d) to cart.%n", product.getName(), quantity);
    }

    // Remove item from cart
    public void removeFromCart(int productId) {
        Iterator<CartItem> it = cart.iterator();
        while (it.hasNext()) {
            CartItem item = it.next();
            if (item.getProduct().getProductId() == productId) {
                System.out.println("  ✅ Removed '" + item.getProduct().getName() + "' from cart.");
                it.remove();
                return;
            }
        }
        System.out.println("  ❌ Item not found in cart.");
    }

    // View cart
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("  🛒 Your cart is empty.");
            return;
        }
        System.out.println("\n" + "─".repeat(55));
        System.out.println("  🛒  YOUR SHOPPING CART");
        System.out.println("─".repeat(55));
        for (CartItem item : cart) {
            System.out.println("  " + item);
        }
        System.out.println("─".repeat(55));
        System.out.printf("  CART TOTAL : Rs. %.2f%n", getTotal());
        System.out.println("─".repeat(55));
    }

    // Calculate total
    public double getTotal() {
        double total = 0;
        for (CartItem item : cart) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Clear cart
    public void clearCart() { cart.clear(); }

    // Get cart items
    public List<CartItem> getCartItems() { return new ArrayList<>(cart); }

    public boolean isEmpty() { return cart.isEmpty(); }
}