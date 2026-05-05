package shopease.ui;

import shopease.dao.DBConnection;
import shopease.model.*;
import shopease.service.ShopService;

import java.util.Scanner;

// ── OOP: Main class — entry point ──
public class Main {

    public static void main(String[] args) {

        ShopService service = new ShopService();
        Scanner     sc      = new Scanner(System.in);

        printBanner();

        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     🛍  ShopEase Platform     ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Login                    ║");
            System.out.println("║  2. Register as Customer     ║");
            System.out.println("║  0. Exit                     ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Choice: ");

            int choice = readInt(sc);
            switch (choice) {
                case 1 -> handleLogin(service, sc);
                case 2 -> handleRegister(service, sc);
                case 0 -> running = false;
                default -> System.out.println("❌ Invalid choice.");
            }
        }

        System.out.println("\nThank you for using ShopEase! Goodbye. 👋");
        DBConnection.closeConnection();
        sc.close();
    }

    // ── Login flow ──
    private static void handleLogin(ShopService service, Scanner sc) {
        System.out.print("Email   : ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        User user = service.login(email, password);

        if (user == null) {
            System.out.println("❌ Invalid email or password.");
            return;
        }

        System.out.println("\n✅ Welcome, " + user.getName() + "! [" + user.getRole() + "]");

        // ── OOP: Polymorphism — same method call, different behaviour ──
        if (user instanceof Admin admin) {
            new AdminUI(service, admin, sc).show();
        } else if (user instanceof Customer customer) {
            new CustomerUI(service, customer, sc).show();
        }
    }

    // ── Register flow ──
    private static void handleRegister(ShopService service, Scanner sc) {
        System.out.println("\n── New Customer Registration ──");
        System.out.print("Full Name : ");
        String name = sc.nextLine().trim();
        System.out.print("Email     : ");
        String email = sc.nextLine().trim();
        System.out.print("Password  : ");
        String password = sc.nextLine().trim();
        System.out.print("Phone     : ");
        String phone = sc.nextLine().trim();
        System.out.print("City      : ");
        String city = sc.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Name, email and password are required.");
            return;
        }
        if (password.length() < 6) {
            System.out.println("❌ Password must be at least 6 characters.");
            return;
        }

        service.registerCustomer(name, email, password, phone, city);
    }

    private static int readInt(Scanner sc) {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                                                  ║");
        System.out.println("║        🛍  ShopEase — Online Shopping           ║");
        System.out.println("║        Java OOP + JDBC Console Application       ║");
        System.out.println("║                                                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  Demo Accounts:                                  ║");
        System.out.println("║  Admin    → admin@shopease.com  / admin123       ║");
        System.out.println("║  Customer → rahul@shopease.com  / pass123        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
