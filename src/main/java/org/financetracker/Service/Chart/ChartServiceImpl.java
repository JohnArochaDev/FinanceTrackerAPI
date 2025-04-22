package org.financetracker.Service.Chart;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;
import org.financetracker.Modal.Finance;
import org.financetracker.Repository.ChartRepository;
import org.financetracker.Repository.DatasetRepository;
import org.financetracker.Repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartRepository chartRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private FinanceRepository financeRepository;

    @Override
    public Chart saveChart(UUID financeId, Chart chart) {
        Optional<Finance> finance = financeRepository.findById(financeId);
        if (finance.isEmpty()) {
            throw new RuntimeException("Finance record not found with ID: " + financeId);
        }

        chart.setFinance(finance.get());
        return chartRepository.save(chart);
    }

    @Override
    public Chart updateChart(UUID chartId, Chart chart) {
        Optional<Chart> existingChart = chartRepository.findById(chartId);
        if (existingChart.isEmpty()) {
            throw new RuntimeException("Chart not found with ID: " + chartId);
        }

        Chart updatedChart = existingChart.get();
        updatedChart.setLabels(chart.getLabels());
        updatedChart.setDatasets(chart.getDatasets());

        return chartRepository.save(updatedChart);
    }

    @Override
    public void deleteChart(UUID chartId) {
        Optional<Chart> chart = chartRepository.findById(chartId);
        if (chart.isEmpty()) {
            throw new RuntimeException("Chart not found with ID: " + chartId);
        }

        chartRepository.delete(chart.get());
    }

    @Override
    public Chart getChartById(UUID chartId) {
        return chartRepository.findById(chartId)
            .orElseThrow(() -> new RuntimeException("Chart not found with ID: " + chartId));
    }

    @Override
    public List<Chart> getChartsByFinanceId(UUID financeId) {
        Optional<Finance> finance = financeRepository.findById(financeId);
        if (finance.isEmpty()) {
            throw new RuntimeException("Finance record not found with ID: " + financeId);
        }

        return finance.get().getCharts();
    }

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