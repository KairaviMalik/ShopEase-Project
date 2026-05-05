package shopease.model;

// ── OOP: Encapsulation ──
public class ReturnRequest {
    private int    id;
    private int    orderId;
    private int    customerId;
    private String customerName;
    private String productName;
    private String reason;
    private String status;   // Pending / Approved / Rejected
    private String requestDate;

    public ReturnRequest() {}

    public ReturnRequest(int id, int orderId, int customerId, String customerName,
                         String productName, String reason, String status, String requestDate) {
        this.id           = id;
        this.orderId      = orderId;
        this.customerId   = customerId;
        this.customerName = customerName;
        this.productName  = productName;
        this.reason       = reason;
        this.status       = status;
        this.requestDate  = requestDate;
    }

    public int    getId()                   { return id; }
    public void   setId(int id)             { this.id = id; }

    public int    getOrderId()              { return orderId; }
    public void   setOrderId(int orderId)   { this.orderId = orderId; }

    public int    getCustomerId()           { return customerId; }
    public void   setCustomerId(int id)     { this.customerId = id; }

    public String getCustomerName()                     { return customerName; }
    public void   setCustomerName(String customerName)  { this.customerName = customerName; }

    public String getProductName()                  { return productName; }
    public void   setProductName(String productName){ this.productName = productName; }

    public String getReason()               { return reason; }
    public void   setReason(String reason)  { this.reason = reason; }

    public String getStatus()               { return status; }
    public void   setStatus(String status)  { this.status = status; }

    public String getRequestDate()                  { return requestDate; }
    public void   setRequestDate(String requestDate){ this.requestDate = requestDate; }

    @Override
    public String toString() {
        return String.format("Return#%-4d | Order#%-4d | %-15s | %-20s | %-20s | %s",
                id, orderId, customerName, productName, reason, status);
    }
}
