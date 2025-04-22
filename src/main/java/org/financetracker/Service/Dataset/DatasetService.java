package org.financetracker.Service.Dataset;

import org.financetracker.Modal.Dataset;

import java.util.UUID;

public interface DatasetService {

    // Save a new dataset for a chart
    Dataset saveDataset(UUID chartId, Dataset dataset);

    // Update an existing dataset
    Dataset updateDataset(UUID datasetId, Dataset dataset);

    // Delete a dataset by its ID
    void deleteDataset(UUID datasetId);

    // Get a dataset by its ID
    Dataset getDatasetById(UUID datasetId);
}