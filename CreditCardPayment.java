package payment;

// POLYMORPHISM: Credit card implements Payment
public class CreditCardPayment implements Payment {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("  💳 Processing Credit Card payment...");
        System.out.printf("     Card: **** **** **** %s | Holder: %s%n",
                cardNumber.substring(cardNumber.length() - 4), cardHolder);
        System.out.printf("     Amount Charged: Rs. %.2f%n", amount);
        System.out.println("  ✅ Credit Card Payment Successful!");
        return true;
    }

    @Override
    public String getPaymentMethod() { return "Credit Card"; }
}