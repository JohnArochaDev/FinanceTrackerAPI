package org.financetracker.Service.Finance;

import org.financetracker.Modal.Chart;
import org.financetracker.Modal.Dataset;
import org.financetracker.Modal.Finance;
import org.financetracker.Modal.User;
import org.financetracker.Repository.ChartRepository;
import org.financetracker.Repository.DatasetRepository;
import org.financetracker.Repository.FinanceRepository;
import org.financetracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChartRepository chartRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    public Finance saveFinance(UUID userId, Finance finance) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        finance.setUser(user.get());
        finance.getCharts().forEach(chart -> {
            chart.setFinance(finance); // Set finance reference in each chart
            chart.getDatasets().forEach(dataset -> {
                dataset.setChart(chart); // Set chart reference in each dataset
            });
        });
        return financeRepository.save(finance);
    }


    @Override
    public Finance updateFinance(UUID financeId, Finance finance) {
        Optional<Finance> existingFinance = financeRepository.findById(financeId);
        if (existingFinance.isEmpty()) {
            throw new RuntimeException("Finance record not found with ID: " + financeId);
        }

        Finance updatedFinance = existingFinance.get();
        updatedFinance.setTotalIncome(finance.getTotalIncome());
        updatedFinance.setTotalExpenses(finance.getTotalExpenses());
        updatedFinance.setDeficit(finance.getDeficit());
        updatedFinance.setRemaining(finance.getRemaining());
        updatedFinance.setTotalSavings(finance.getTotalSavings());
        updatedFinance.setTotalDebt(finance.getTotalDebt());

        return financeRepository.save(updatedFinance);
    }

    @Override
    public void deleteFinance(UUID financeId) {
        Optional<Finance> finance = financeRepository.findById(financeId);
        if (finance.isEmpty()) {
            throw new RuntimeException("Finance record not found with ID: " + financeId);
        }

        financeRepository.delete(finance.get());
    }

    @Override
    public Finance getFinanceById(UUID financeId) {
        return financeRepository.findById(financeId)
            .orElseThrow(() -> new RuntimeException("Finance record not found with ID: " + financeId));
    }

    @Override
    public Finance getFinanceByUserId(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        return user.get().getFinance();
    }

    @Override
    public List<Finance> getAllFinances() {
        return financeRepository.findAll();
    }

    @Override
    public Chart addChartToFinance(UUID financeId, Chart chart) {
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
}