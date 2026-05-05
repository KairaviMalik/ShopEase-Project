package shopease.model;

// ── OOP: Inheritance — Customer extends User ──
public class Customer extends User {
    private String riskLevel;   // low / medium / high
    private int    totalOrders;
    private int    totalReturns;
    private boolean blocked;
    private String joinedDate;

    public Customer() { super(); }

    public Customer(int id, String name, String email, String password,
                    String phone, String city, String riskLevel,
                    int totalOrders, int totalReturns, boolean blocked, String joinedDate) {
        super(id, name, email, password, phone, city);
        this.riskLevel    = riskLevel;
        this.totalOrders  = totalOrders;
        this.totalReturns = totalReturns;
        this.blocked      = blocked;
        this.joinedDate   = joinedDate;
    }

    // ── OOP: Polymorphism — overriding abstract method ──
    @Override
    public String getRole() { return "Customer"; }

    public String  getRiskLevel()               { return riskLevel; }
    public void    setRiskLevel(String risk)    { this.riskLevel = risk; }

    public int     getTotalOrders()             { return totalOrders; }
    public void    setTotalOrders(int n)        { this.totalOrders = n; }

    public int     getTotalReturns()            { return totalReturns; }
    public void    setTotalReturns(int n)       { this.totalReturns = n; }

    public boolean isBlocked()                  { return blocked; }
    public void    setBlocked(boolean blocked)  { this.blocked = blocked; }

    public String  getJoinedDate()              { return joinedDate; }
    public void    setJoinedDate(String d)      { this.joinedDate = d; }
}
