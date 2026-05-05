package shopease.model;

// ── OOP: Inheritance — Admin extends User ──
public class Admin extends User {

    public Admin() { super(); }

    public Admin(int id, String name, String email, String password, String phone, String city) {
        super(id, name, email, password, phone, city);
    }

    // ── OOP: Polymorphism ──
    @Override
    public String getRole() { return "Admin"; }
}
