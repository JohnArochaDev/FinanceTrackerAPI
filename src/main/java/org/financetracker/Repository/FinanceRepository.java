package org.financetracker.Repository;
import org.financetracker.Modal.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinanceRepository extends JpaRepository<Finance, Integer> {
    List<Finance> findByLanguage(String language);

    Optional<Finance> findById(UUID id);
}
