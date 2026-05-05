package payment;

// ABSTRACTION via Interface: All payment modes implement this
public interface Payment {
    boolean processPayment(double amount);
    String getPaymentMethod();
}
