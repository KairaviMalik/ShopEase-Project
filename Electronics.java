package model;

// INHERITANCE: Electronics extends Product
public class Electronics extends Product {
    private String brand;
    private int warrantyMonths;

    public Electronics(int productId, String name, double price, int stock,
                       String brand, int warrantyMonths) {
        super(productId, name, price, stock, "Electronics");
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    // POLYMORPHISM: Override getDescription()
    @Override
    public String getDescription() {
        return String.format("Brand: %s | Warranty: %d months", brand, warrantyMonths);
    }

    public String getBrand()            { return brand; }
    public int getWarrantyMonths()      { return warrantyMonths; }
}
