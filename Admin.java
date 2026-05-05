package model;

// INHERITANCE: Admin extends User
public class Admin extends User {
    private String department;

    public Admin(int userId, String name, String email,
                 String password, String department) {
        super(userId, name, email, password);
        this.department = department;
    }

    @Override
    public String getRole() { return "Admin"; }

    public String getDepartment() { return department; }
}
