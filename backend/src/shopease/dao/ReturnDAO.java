package shopease.dao;

import shopease.model.ReturnRequest;
import java.util.List;

// ── OOP: Interface ──
public interface ReturnDAO {
    void              submitReturn(ReturnRequest r);
    List<ReturnRequest> getAllReturns();
    List<ReturnRequest> getReturnsByCustomer(int customerId);
    void              updateReturnStatus(int returnId, String status);
}
