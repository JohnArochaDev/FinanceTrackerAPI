package org.financetracker.Service.Chart;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;

import java.util.List;
import java.util.UUID;

public interface ChartService {

    // Save a new chart for a finance record
    Chart saveChart(UUID financeId, Chart chart);

    // Update an existing chart
    Chart updateChart(UUID chartId, Chart chart);

    // Delete a chart by its ID
    void deleteChart(UUID chartId);

    // Get a chart by its ID
    Chart getChartById(UUID chartId);

    // Get all charts for a specific finance record
    List<Chart> getChartsByFinanceId(UUID financeId);

    // Save a dataset for a chart
    Dataset saveDataset(UUID chartId, Dataset dataset);

    // Update an existing dataset
    Dataset updateDataset(UUID datasetId, Dataset dataset);

    // Delete a dataset by its ID
    void deleteDataset(UUID datasetId);

    // Get a dataset by its ID
    Dataset getDatasetById(UUID datasetId);
}