package model;

// INHERITANCE: Book extends Product
public class Book extends Product {
    private String author;
    private String isbn;

    public Book(int productId, String name, double price, int stock,
                String author, String isbn) {
        super(productId, name, price, stock, "Books");
        this.author = author;
        this.isbn = isbn;
    }

    // POLYMORPHISM: Override getDescription()
    @Override
    public String getDescription() {
        return String.format("Author: %s | ISBN: %s", author, isbn);
    }

    public String getAuthor() { return author; }
    public String getIsbn()   { return isbn; }
}
