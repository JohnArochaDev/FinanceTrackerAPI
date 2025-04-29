package org.financetracker.Service.Finance;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Finance;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface FinanceService {

    // Save a new finance record
    ResponseEntity<String> saveFinance(UUID userId, Finance finance);

    // Update an existing finance record
    Finance updateFinance(UUID financeId, Finance finance);

    // Delete a finance record by its ID
    void deleteFinance(UUID financeId);

    // Get a finance record by its ID
    Finance getFinanceById(UUID financeId);

    // Get a finance record by user ID
    Finance getFinanceByUserId(UUID userId);

    // Get all finance records (for admin purposes)
    List<Finance> getAllFinances();

    // Add a chart to a finance record
    Chart addChartToFinance(UUID financeId, Chart chart);

    // Update a chart in a finance record
    Chart updateChart(UUID chartId, Chart chart);

    // Delete a chart from a finance record
    void deleteChart(UUID chartId);
}