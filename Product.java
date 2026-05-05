package model;

// ABSTRACTION: Abstract class defining product blueprint
public abstract class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private String category;

    // Constructor
    public Product(int productId, String name, double price, int stock, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // ABSTRACTION: Abstract method to be implemented by subclasses
    public abstract String getDescription();

    // ENCAPSULATION: Getters and Setters
    public int getProductId()             { return productId; }
    public String getName()               { return name; }
    public void setName(String name)      { this.name = name; }
    public double getPrice()              { return price; }
    public void setPrice(double price)    { this.price = price; }
    public int getStock()                 { return stock; }
    public void setStock(int stock)       { this.stock = stock; }
    public String getCategory()           { return category; }
    public void setCategory(String cat)   { this.category = cat; }

    public boolean isAvailable()          { return stock > 0; }

    @Override
    public String toString() {
        return String.format("[ID:%d] %-20s | Rs.%-8.2f | Stock: %d | %s",
                productId, name, price, stock, category);
    }
}