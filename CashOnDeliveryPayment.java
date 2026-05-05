package payment;

// POLYMORPHISM: COD implements Payment
public class CashOnDeliveryPayment implements Payment {
    private String deliveryAddress;

    public CashOnDeliveryPayment(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("  🚚 Cash on Delivery selected...");
        System.out.printf("     Delivery to: %s%n", deliveryAddress);
        System.out.printf("     Amount to collect: Rs. %.2f%n", amount);
        System.out.println("  ✅ COD Order Confirmed!");
        return true;
    }

    @Override
    public String getPaymentMethod() { return "Cash on Delivery"; }
}