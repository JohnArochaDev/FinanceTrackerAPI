package org.financetracker.Controller;

import org.financetracker.Modal.Dataset;
import org.financetracker.Service.Dataset.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/datasets")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    // Save a new dataset for a chart
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{chartId}")
    public Dataset saveDataset(@PathVariable UUID chartId, @RequestBody Dataset dataset) {
        return datasetService.saveDataset(chartId, dataset);
    }

    // Update an existing dataset
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{datasetId}")
    public Dataset updateDataset(@PathVariable UUID datasetId, @RequestBody Dataset dataset) {
        return datasetService.updateDataset(datasetId, dataset);
    }

    // Delete a dataset by its ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{datasetId}")
    public void deleteDataset(@PathVariable UUID datasetId) {
        datasetService.deleteDataset(datasetId);
    }

    // Get a dataset by its ID
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{datasetId}")
    public Dataset getDatasetById(@PathVariable UUID datasetId) {
        return datasetService.getDatasetById(datasetId);
    }
}