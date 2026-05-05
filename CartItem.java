package model;

// ENCAPSULATION: CartItem wraps product + quantity
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct()           { return product; }
    public int getQuantity()              { return quantity; }
    public void setQuantity(int qty)      { this.quantity = qty; }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-22s x%d  @ Rs.%-8.2f = Rs.%.2f",
                product.getName(), quantity, product.getPrice(), getSubtotal());
    }
}