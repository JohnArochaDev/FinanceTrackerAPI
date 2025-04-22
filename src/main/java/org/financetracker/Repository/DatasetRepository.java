package org.financetracker.Repository;

import org.financetracker.Modal.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DatasetRepository extends JpaRepository<Dataset, UUID> {
    Optional<Dataset> findById(UUID id);
}