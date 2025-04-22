package org.financetracker.Service.Dataset;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;
import org.financetracker.Repository.ChartRepository;
import org.financetracker.Repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private ChartRepository chartRepository;

    @Override
    public Dataset saveDataset(UUID chartId, Dataset dataset) {
        Optional<Chart> chart = chartRepository.findById(chartId);
        if (chart.isEmpty()) {
            throw new RuntimeException("Chart not found with ID: " + chartId);
        }

        dataset.setChart(chart.get());
        return datasetRepository.save(dataset);
    }

    @Override
    public Dataset updateDataset(UUID datasetId, Dataset dataset) {
        Optional<Dataset> existingDataset = datasetRepository.findById(datasetId);
        if (existingDataset.isEmpty()) {
            throw new RuntimeException("Dataset not found with ID: " + datasetId);
        }

        Dataset updatedDataset = existingDataset.get();
        updatedDataset.setLabel(dataset.getLabel());
        updatedDataset.setData(dataset.getData());
        updatedDataset.setBackgroundColor(dataset.getBackgroundColor());
        updatedDataset.setBorderColor(dataset.getBorderColor());
        updatedDataset.setBorderWidth(dataset.getBorderWidth());

        return datasetRepository.save(updatedDataset);
    }

    @Override
    public void deleteDataset(UUID datasetId) {
        Optional<Dataset> dataset = datasetRepository.findById(datasetId);
        if (dataset.isEmpty()) {
            throw new RuntimeException("Dataset not found with ID: " + datasetId);
        }

        datasetRepository.delete(dataset.get());
    }

    @Override
    public Dataset getDatasetById(UUID datasetId) {
        return datasetRepository.findById(datasetId)
            .orElseThrow(() -> new RuntimeException("Dataset not found with ID: " + datasetId));
    }
}