package org.financetracker.Controller;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;
import org.financetracker.Modal.Finance;
import org.financetracker.Service.Chart.ChartService;
import org.financetracker.Service.Dataset.DatasetService;
import org.financetracker.Service.Finance.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private ChartService chartService;

    @Autowired
    private DatasetService datasetService;

    private static final String ENCRYPTED_KEY = System.getenv("ENCRYPTED_KEY");


    // Finance Endpoints
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{userId}")
    public Finance saveFinance(@PathVariable UUID userId, @RequestBody Finance finance) throws Exception {
        // Encrypt sensitive fields before saving
        finance.encryptFields(ENCRYPTED_KEY);

        return financeService.saveFinance(userId, finance);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{financeId}")
    public Finance updateFinance(@PathVariable UUID financeId, @RequestBody Finance finance) throws Exception {
        // Encrypt sensitive fields before updating
        finance.encryptFields(ENCRYPTED_KEY);

        return financeService.updateFinance(financeId, finance);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{financeId}")
    public void deleteFinance(@PathVariable UUID financeId) {
        financeService.deleteFinance(financeId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{financeId}")
    public Finance getFinanceById(@PathVariable UUID financeId) throws Exception {
        Finance finance = financeService.getFinanceById(financeId);
        // Decrypt sensitive fields before returning
        finance.decryptFields(ENCRYPTED_KEY);

        return finance;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}")
    public Finance getFinanceByUserId(@PathVariable UUID userId) throws Exception {
        Finance finance = financeService.getFinanceByUserId(userId);
        // Decrypt sensitive fields before returning
        finance.decryptFields(ENCRYPTED_KEY);

        return finance;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Finance> getAllFinances() {
        List<Finance> finances = financeService.getAllFinances();
        // Decrypt sensitive fields for all records
        finances.forEach(finance -> {
            try {
                finance.decryptFields(ENCRYPTED_KEY);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return finances;
    }

    // Chart Endpoints
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{financeId}/charts")
    public Chart saveChart(@PathVariable UUID financeId, @RequestBody Chart chart) throws Exception {
        // Encrypt each label and save the list
        chart.encryptLabels(ENCRYPTED_KEY);

        return chartService.saveChart(financeId, chart);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/charts/{chartId}")
    public Chart updateChart(@PathVariable UUID chartId, @RequestBody Chart chart) {
        // Encrypt each label and update the list
        chart.encryptLabels(ENCRYPTED_KEY);

        return chartService.updateChart(chartId, chart);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/charts/{chartId}")
    public void deleteChart(@PathVariable UUID chartId) {

        chartService.deleteChart(chartId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/charts/{chartId}")
    public Chart getChartById(@PathVariable UUID chartId) throws Exception {
        Chart chart = chartService.getChartById(chartId);
        chart.decryptLabels(ENCRYPTED_KEY);

        return chart;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{financeId}/charts")
    public List<Chart> getChartsByFinanceId(@PathVariable UUID financeId) {
        List<Chart> charts = chartService.getChartsByFinanceId(financeId);

        // Decrypt labels for each chart in the list
        charts.forEach(chart -> chart.decryptLabels(ENCRYPTED_KEY));

        return charts;
    }

    // Dataset Endpoints

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/charts/{chartId}/datasets")
    public Dataset saveDataset(@PathVariable UUID chartId, @RequestBody Dataset dataset) throws Exception {
        dataset.encryptFields(ENCRYPTED_KEY);

        return datasetService.saveDataset(chartId, dataset);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/datasets/{datasetId}")
    public Dataset updateDataset(@PathVariable UUID datasetId, @RequestBody Dataset dataset) {
        dataset.encryptFields(ENCRYPTED_KEY);

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
        Dataset dataset = datasetService.getDatasetById(datasetId);
        dataset.decryptFields(ENCRYPTED_KEY);

        return dataset;
    }
}