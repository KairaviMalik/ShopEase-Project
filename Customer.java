package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String address;
    private List<Order> orderHistory;

    public Customer(int userId, String name, String email,
                    String password, String address) {
        super(userId, name, email, password);
        this.address = address;
        this.orderHistory = new ArrayList<>();
    }

    @Override
    public String getRole() { return "Customer"; }

    public String getAddress()        { return address; }
    public void setAddress(String a)  { this.address = a; }

    public void addOrder(Order o)           { orderHistory.add(o); }
    public List<Order> getOrderHistory()    { return orderHistory; }

    public void printOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("  No orders placed yet.");
            return;
        }
        System.out.println("\n--- Order History for " + getName() + " ---");
        for (Order o : orderHistory) {
            System.out.printf("  #%d | %s | Rs.%.2f | %s | %s%n",
                    o.getOrderId(), o.getOrderDate(),
                    o.getTotalAmount(), o.getPaymentMethod(), o.getStatus());
        }
    }
}