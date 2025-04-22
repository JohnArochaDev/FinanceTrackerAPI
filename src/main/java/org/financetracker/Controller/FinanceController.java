package org.financetracker.Controller;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;
import org.financetracker.Modal.Finance;
import org.financetracker.Service.Chart.ChartService;
import org.financetracker.Service.Dataset.DatasetService;
import org.financetracker.Service.Finance.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private ChartService chartService;

    @Autowired
    private DatasetService datasetService;

    // Finance Endpoints

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{userId}")
    public Finance saveFinance(@PathVariable UUID userId, @RequestBody Finance finance) {
        // Encrypt sensitive fields before saving
//        finance.setCode(encrypt(finance.getCode()));
        return financeService.saveFinance(userId, finance);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{financeId}")
    public Finance updateFinance(@PathVariable UUID financeId, @RequestBody Finance finance) {
        // Encrypt sensitive fields before updating
//        finance.setCode(encrypt(finance.getCode()));
        return financeService.updateFinance(financeId, finance);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{financeId}")
    public void deleteFinance(@PathVariable UUID financeId) {
        financeService.deleteFinance(financeId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{financeId}")
    public Finance getFinanceById(@PathVariable UUID financeId) {
        Finance finance = financeService.getFinanceById(financeId);
        // Decrypt sensitive fields before returning
//        finance.setCode(decrypt(finance.getCode()));
        return finance;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}")
    public Finance getFinanceByUserId(@PathVariable UUID userId) {
        Finance finance = financeService.getFinanceByUserId(userId);
        // Decrypt sensitive fields before returning
//        finance.setCode(decrypt(finance.getCode()));
        return finance;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Finance> getAllFinances() {
        List<Finance> finances = financeService.getAllFinances();
        // Decrypt sensitive fields for all records
//        finances.forEach(finance -> finance.setCode(decrypt(finance.getCode())));
        return finances;
    }

    // Chart Endpoints

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{financeId}/charts")
    public Chart saveChart(@PathVariable UUID financeId, @RequestBody Chart chart) {
        return chartService.saveChart(financeId, chart);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/charts/{chartId}")
    public Chart updateChart(@PathVariable UUID chartId, @RequestBody Chart chart) {
        return chartService.updateChart(chartId, chart);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/charts/{chartId}")
    public void deleteChart(@PathVariable UUID chartId) {
        chartService.deleteChart(chartId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/charts/{chartId}")
    public Chart getChartById(@PathVariable UUID chartId) {
        return chartService.getChartById(chartId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{financeId}/charts")
    public List<Chart> getChartsByFinanceId(@PathVariable UUID financeId) {
        return chartService.getChartsByFinanceId(financeId);
    }

    // Dataset Endpoints

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/charts/{chartId}/datasets")
    public Dataset saveDataset(@PathVariable UUID chartId, @RequestBody Dataset dataset) {
        return datasetService.saveDataset(chartId, dataset);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/datasets/{datasetId}")
    public Dataset updateDataset(@PathVariable UUID datasetId, @RequestBody Dataset dataset) {
        return datasetService.updateDataset(datasetId, dataset);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/datasets/{datasetId}")
    public void deleteDataset(@PathVariable UUID datasetId) {
        datasetService.deleteDataset(datasetId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/datasets/{datasetId}")
    public Dataset getDatasetById(@PathVariable UUID datasetId) {
        return datasetService.getDatasetById(datasetId);
    }
}