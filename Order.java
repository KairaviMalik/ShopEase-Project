package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// ENCAPSULATION: Order captures a confirmed purchase
public class Order {
    private static int counter = 1000;

    private int orderId;
    private String customerName;
    private List<CartItem> items;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private String orderDate;

    public Order(String customerName, List<CartItem> items,
                 double totalAmount, String paymentMethod) {
        this.orderId = ++counter;
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = "Confirmed";
        this.orderDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    // Getters
    public int getOrderId()           { return orderId; }
    public String getCustomerName()   { return customerName; }
    public List<CartItem> getItems()  { return items; }
    public double getTotalAmount()    { return totalAmount; }
    public String getPaymentMethod()  { return paymentMethod; }
    public String getStatus()         { return status; }
    public String getOrderDate()      { return orderDate; }
    public void setStatus(String s)   { this.status = s; }

    public void printReceipt() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("           🛒  ORDER RECEIPT");
        System.out.println("=".repeat(55));
        System.out.printf("  Order ID    : #%d%n", orderId);
        System.out.printf("  Customer    : %s%n", customerName);
        System.out.printf("  Date        : %s%n", orderDate);
        System.out.printf("  Payment     : %s%n", paymentMethod);
        System.out.printf("  Status      : %s%n", status);
        System.out.println("-".repeat(55));
        System.out.println("  ITEMS:");
        for (CartItem item : items) {
            System.out.println("    " + item);
        }
        System.out.println("-".repeat(55));
        System.out.printf("  TOTAL AMOUNT : Rs. %.2f%n", totalAmount);
        System.out.println("=".repeat(55));
        System.out.println("   Thank you for shopping with us! 🎉");
        System.out.println("=".repeat(55) + "\n");
    }
}