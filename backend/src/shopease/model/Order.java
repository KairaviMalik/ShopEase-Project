package shopease.model;

// ── OOP: Encapsulation ──
public class Order {
    private int    id;
    private int    customerId;
    private String customerName;
    private int    productId;
    private String productName;
    private int    quantity;
    private double totalAmount;
    private String paymentMethod;
    private String status;        // Confirmed / Cancelled / Returned
    private String orderDate;

    public Order() {}

    public Order(int id, int customerId, String customerName,
                 int productId, String productName, int quantity,
                 double totalAmount, String paymentMethod,
                 String status, String orderDate) {
        this.id            = id;
        this.customerId    = customerId;
        this.customerName  = customerName;
        this.productId     = productId;
        this.productName   = productName;
        this.quantity      = quantity;
        this.totalAmount   = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status        = status;
        this.orderDate     = orderDate;
    }

    public int    getId()                   { return id; }
    public void   setId(int id)             { this.id = id; }

    public int    getCustomerId()           { return customerId; }
    public void   setCustomerId(int id)     { this.customerId = id; }

    public String getCustomerName()                     { return customerName; }
    public void   setCustomerName(String customerName)  { this.customerName = customerName; }

    public int    getProductId()            { return productId; }
    public void   setProductId(int id)      { this.productId = id; }

    public String getProductName()                  { return productName; }
    public void   setProductName(String name)       { this.productName = name; }

    public int    getQuantity()             { return quantity; }
    public void   setQuantity(int qty)      { this.quantity = qty; }

    public double getTotalAmount()              { return totalAmount; }
    public void   setTotalAmount(double amt)    { this.totalAmount = amt; }

    public String getPaymentMethod()                    { return paymentMethod; }
    public void   setPaymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod; }

    public String getStatus()               { return status; }
    public void   setStatus(String status)  { this.status = status; }

    public String getOrderDate()                { return orderDate; }
    public void   setOrderDate(String orderDate){ this.orderDate = orderDate; }

    @Override
    public String toString() {
        return String.format("Order#%-4d | Customer: %-15s | Product: %-25s | Qty:%-2d | Rs.%-8.0f | %-8s | %s",
                id, customerName, productName, quantity, totalAmount, paymentMethod, status);
    }
}
