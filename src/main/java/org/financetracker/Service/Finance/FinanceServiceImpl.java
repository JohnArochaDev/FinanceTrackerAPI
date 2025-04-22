package org.financetracker.Service.Finance;

import org.financetracker.Modal.Finance;
import org.financetracker.Repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceServiceImpl implements FinanceService {

    private final FinanceRepository financeRepository;

    @Autowired
    public FinanceServiceImpl(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    @Override
    public Finance saveFinance(Finance finance) {
        return financeRepository.save(finance);
    }

    @Override
    public Finance updateFinance(Integer id, Finance finance) {
        Optional<Finance> financeOptional = financeRepository.findById(id);
        if (financeOptional.isPresent()) {
            Finance updatedFinance = financeOptional.get();
            updatedFinance.setCode(finance.getCode());
            updatedFinance.setLanguage(finance.getLanguage());
            return financeRepository.save(updatedFinance);
        } else {
            throw new RuntimeException("Finance not found");
        }
    }

    @Override
    public void deleteFinance(Finance finance) {
        Optional<Finance> optionalFinance = financeRepository.findById(finance.getId());
        if (optionalFinance.isPresent()) {
            financeRepository.delete(finance);
        } else {
            throw new RuntimeException("Finance not found.");
        }
    }

    @Override
    public List<Finance> findAll() {
        List<Finance> foundFinances = financeRepository.findAll();
        if (foundFinances.isEmpty()) {
            throw new RuntimeException("Finances not found");
        } else {
            return foundFinances;
        }
    }

    @Override
    public List<Finance> findByLanguage(String language) {
        List<Finance> foundFinances = financeRepository.findByLanguage(language);
        if (foundFinances.isEmpty()) {
            throw new RuntimeException("Finances not found");
        } else {
            return foundFinances;
        }
    }

    public Optional<Finance> findById(Integer id) {
        Optional<Finance> foundFinance = financeRepository.findById(id);
        if (foundFinance.isEmpty()) {
            throw new RuntimeException("Finance not found");
        }
        return foundFinance;
    }
}
