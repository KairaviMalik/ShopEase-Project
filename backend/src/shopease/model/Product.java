package shopease.model;

// ── OOP: Encapsulation ──
public class Product {
    private int    id;
    private String name;
    private double price;
    private int    stock;
    private String category;
    private String description;
    private double rating;

    public Product() {}

    public Product(int id, String name, double price, int stock,
                   String category, String description, double rating) {
        this.id          = id;
        this.name        = name;
        this.price       = price;
        this.stock       = stock;
        this.category    = category;
        this.description = description;
        this.rating      = rating;
    }

    public int    getId()               { return id; }
    public void   setId(int id)         { this.id = id; }

    public String getName()             { return name; }
    public void   setName(String name)  { this.name = name; }

    public double getPrice()                { return price; }
    public void   setPrice(double price)    { this.price = price; }

    public int    getStock()                { return stock; }
    public void   setStock(int stock)       { this.stock = stock; }

    public String getCategory()                 { return category; }
    public void   setCategory(String category)  { this.category = category; }

    public String getDescription()                  { return description; }
    public void   setDescription(String description){ this.description = description; }

    public double getRating()               { return rating; }
    public void   setRating(double rating)  { this.rating = rating; }

    @Override
    public String toString() {
        return String.format("%-4d %-30s Rs.%-8.0f Stock:%-4d %-12s ★%.1f",
                id, name, price, stock, category, rating);
    }
}
