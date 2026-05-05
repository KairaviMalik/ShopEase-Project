package shopease.dao;

import shopease.model.Customer;
import shopease.model.User;
import java.util.List;

// ── OOP: Interface — defines contract for all user DB operations ──
public interface UserDAO {
    void           registerCustomer(Customer customer);
    User           login(String email, String password);
    Customer       getCustomerById(int id);
    List<Customer> getAllCustomers();
    void           updateCustomerRisk(int customerId, String riskLevel);
    void           blockCustomer(int customerId, boolean blocked);
    void           updateCustomerStats(int customerId, int orders, int returns);
}
