package org.financetracker.Service.Finance;

import org.financetracker.Modal.Finance;

import java.util.List;
import java.util.Optional;

public interface FinanceService {
    // Save
    Finance saveFinance(Finance finance);

    // Update
    Finance updateFinance(Integer id, Finance finance);

    // Delete
    void deleteFinance(Finance finance);

    // All
    List<Finance> findByLanguage(String language);
    List<Finance> findAll();
    Optional<Finance> findById(Integer id);

}
