package model;

// ABSTRACTION + INHERITANCE base: User hierarchy
public abstract class User {
    private int userId;
    private String name;
    private String email;
    private String password;

    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // ABSTRACTION
    public abstract String getRole();

    // ENCAPSULATION
    public int getUserId()            { return userId; }
    public String getName()           { return name; }
    public String getEmail()          { return email; }

    public boolean authenticate(String pwd) {
        return this.password.equals(pwd);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", getRole(), name, email);
    }
}
