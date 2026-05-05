package payment;

// POLYMORPHISM: UPI implements Payment
public class UPIPayment implements Payment {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("  📱 Processing UPI payment...");
        System.out.printf("     UPI ID: %s%n", upiId);
        System.out.printf("     Amount: Rs. %.2f%n", amount);
        System.out.println("  ✅ UPI Payment Successful!");
        return true;
    }

    @Override
    public String getPaymentMethod() { return "UPI"; }
}