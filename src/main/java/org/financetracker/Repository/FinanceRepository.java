package org.financetracker.Repository;
import org.financetracker.Modal.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Integer> {
    List<Finance> findByLanguage(String language);
}
