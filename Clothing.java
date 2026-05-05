package model;

// INHERITANCE: Clothing extends Product
public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(int productId, String name, double price, int stock,
                    String size, String color) {
        super(productId, name, price, stock, "Clothing");
        this.size = size;
        this.color = color;
    }

    // POLYMORPHISM: Override getDescription()
    @Override
    public String getDescription() {
        return String.format("Size: %s | Color: %s", size, color);
    }

    public String getSize()   { return size; }
    public String getColor()  { return color; }
}
