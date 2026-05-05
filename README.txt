===================================================
  ShopEase — Java OOP + JDBC Project
===================================================

WHAT IS IN THIS ZIP
-------------------
ShopEase_Project/
├── frontend/
│   └── ShopEase.html         ← Open this in browser for the UI demo
├── backend/
│   ├── src/shopease/
│   │   ├── model/            ← OOP: User, Customer, Admin, Product, Order, ReturnRequest
│   │   ├── dao/              ← JDBC: Database access interfaces + implementations
│   │   ├── service/          ← Business logic: ShopService.java
│   │   └── ui/               ← Console UI: Main.java, CustomerUI.java, AdminUI.java
│   ├── lib/                  ← PUT mysql-connector-j.jar HERE
│   └── .vscode/              ← VS Code run config
├── database_setup.sql        ← Run this in MySQL first
└── README.txt                ← This file

HOW TO PRESENT THIS PROJECT
----------------------------
1. Show the HTML file in browser → this is your UI / frontend demo
2. Show the Java code in VS Code → this shows OOP + JDBC
3. Run the Java console app → this shows it working with real MySQL

===================================================
STEP 1 — Set up MySQL database
===================================================
1. Open MySQL Workbench
2. Connect with your root password
3. Click File → Open SQL Script
4. Open database_setup.sql from this folder
5. Click the lightning bolt ⚡ to run it
6. You should see: Setup complete! total_users=5 total_products=23

===================================================
STEP 2 — Download JDBC driver JAR
===================================================
1. Go to: https://dev.mysql.com/downloads/connector/j/
2. Select Platform Independent → Download ZIP
3. Extract the ZIP
4. Copy mysql-connector-j-X.X.X.jar
5. Paste it into the backend/lib/ folder

===================================================
STEP 3 — Edit your MySQL password
===================================================
1. Open backend/src/shopease/dao/DBConnection.java
2. Find line: private static final String PASSWORD = "your_password_here";
3. Change your_password_here to your actual MySQL password
4. Save the file

===================================================
STEP 4 — Open backend in VS Code and Run
===================================================
1. Open VS Code
2. File → Open Folder → select the backend folder
3. Open src/shopease/ui/Main.java
4. Click ▶ Run above public static void main
5. Terminal shows the ShopEase console menu

===================================================
STEP 5 — Open the HTML frontend (separate, anytime)
===================================================
1. Go to frontend/ folder
2. Double-click ShopEase.html
3. It opens in your browser
4. This works completely on its own — no Java needed

===================================================
DEMO ACCOUNTS
===================================================
Admin    : admin@shopease.com / admin123
Customer : rahul@shopease.com / pass123
Customer : priya@shopease.com / pass123
Customer : amit@shopease.com  / pass123
Customer : kavya@shopease.com / pass123  (blocked)

===================================================
OOP CONCEPTS USED (for viva)
===================================================
Abstraction  → User.java (abstract class), UserDAO/ProductDAO/OrderDAO/ReturnDAO (interfaces)
Inheritance  → Customer extends User, Admin extends User
Polymorphism → login() returns User type, cast to Admin or Customer at runtime
Encapsulation→ All fields private with getters/setters in every model class
Interface    → UserDAO, ProductDAO, OrderDAO, ReturnDAO
Composition  → ShopService HAS-A UserDAO, ProductDAO, OrderDAO, ReturnDAO
Singleton    → DBConnection (one shared DB connection for whole app)
JDBC         → All DAOImpl classes use PreparedStatement, ResultSet, Connection
