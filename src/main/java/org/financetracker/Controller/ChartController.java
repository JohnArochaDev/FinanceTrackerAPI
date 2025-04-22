package org.financetracker.Controller;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;
import org.financetracker.Service.Chart.ChartService;
import org.financetracker.Service.Dataset.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private DatasetService datasetService;

    // Chart Endpoints

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{financeId}")
    public Chart saveChart(@PathVariable UUID financeId, @RequestBody Chart chart) {
        return chartService.saveChart(financeId, chart);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{chartId}")
    public Chart updateChart(@PathVariable UUID chartId, @RequestBody Chart chart) {
        return chartService.updateChart(chartId, chart);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{chartId}")
    public void deleteChart(@PathVariable UUID chartId) {
        chartService.deleteChart(chartId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{chartId}")
    public Chart getChartById(@PathVariable UUID chartId) {
        return chartService.getChartById(chartId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/finance/{financeId}")
    public List<Chart> getChartsByFinanceId(@PathVariable UUID financeId) {
        return chartService.getChartsByFinanceId(financeId);
    }

    // Dataset Endpoints

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{chartId}/datasets")
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