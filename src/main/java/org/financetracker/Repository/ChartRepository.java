package org.financetracker.Repository;

import org.financetracker.Modal.Chart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChartRepository extends JpaRepository<Chart, UUID> {
    Optional<Chart> findById(UUID id);
}