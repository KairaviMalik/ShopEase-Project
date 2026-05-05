# 🛒 Online Shopping System — Java OOP

## Project Structure
```
OnlineShoppingSystem/
└── src/
    ├── Main.java                        ← Entry point / Demo driver
    ├── model/
    │   ├── Product.java                 ← Abstract base class (Abstraction)
    │   ├── Electronics.java             ← Subclass (Inheritance + Polymorphism)
    │   ├── Clothing.java                ← Subclass
    │   ├── Book.java                    ← Subclass
    │   ├── User.java                    ← Abstract base class
    │   ├── Customer.java                ← Subclass (Inheritance)
    │   ├── Admin.java                   ← Subclass
    │   ├── CartItem.java                ← Encapsulation model
    │   └── Order.java                   ← Order model with receipt
    ├── payment/
    │   ├── Payment.java                 ← Interface (Abstraction)
    │   ├── CreditCardPayment.java       ← Implements Payment (Polymorphism)
    │   ├── UPIPayment.java              ← Implements Payment
    │   └── CashOnDeliveryPayment.java   ← Implements Payment
    └── service/
        ├── ProductService.java          ← Admin: Inventory management
        ├── CartService.java             ← Customer: Cart operations
        └── OrderService.java            ← Order placement & tracking
```

## How to Compile & Run
```bash
# From the src/ directory
javac -d out -sourcepath . Main.java model/*.java payment/*.java service/*.java
java -cp out Main
```

## OOP Concepts Used

| Concept         | Where Applied |
|-----------------|---------------|
| Encapsulation   | All model classes — private fields + getters/setters |
| Inheritance     | Electronics/Clothing/Book → Product; Customer/Admin → User |
| Polymorphism    | getDescription() overridden; Payment interface implementations |
| Abstraction     | Abstract Product, User classes; Payment interface |
| Interface       | Payment interface with 3 implementations |
| Service Layer   | ProductService, CartService, OrderService |

## 👤 Author

**Name:** <!-- Kairavi Malik -->  
**GitHub:** <!-- @KairaviMalik -->  
**Institution:** <!-- University Of Petroleum & Energy Studies  -->  

## 🎓 Mentor

**Name:** <!-- Dr.Kalluri Shareef Babu -->  
**Designation:** <!-- Assistant Professor -->  
**Institution:** <!-- UPES-->
