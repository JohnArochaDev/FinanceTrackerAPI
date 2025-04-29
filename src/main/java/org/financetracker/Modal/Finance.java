package org.financetracker.Modal;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private double totalIncome;

    @Column(nullable = false)
    private double totalExpenses;

    @Column(nullable = false)
    private double deficit;

    @Column(nullable = false)
    private double remaining;

    @Column(nullable = false)
    private double totalSavings;

    @Column(nullable = false)
    private double totalDebt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "finance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chart> charts;

    // Constructors
    public Finance() {}

    public Finance(double totalIncome, double totalExpenses, double deficit, double remaining, double totalSavings, double totalDebt, String code) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.deficit = deficit;
        this.remaining = remaining;
        this.totalSavings = totalSavings;
        this.totalDebt = totalDebt;
    }

    // Methods
    public void encryptFields(String encryptionKey) throws Exception {
        this.totalIncome = Double.parseDouble(encrypt(String.valueOf(this.totalIncome), encryptionKey));
        this.totalExpenses = Double.parseDouble(encrypt(String.valueOf(this.totalExpenses), encryptionKey));
        this.deficit = Double.parseDouble(encrypt(String.valueOf(this.deficit), encryptionKey));
        this.remaining = Double.parseDouble(encrypt(String.valueOf(this.remaining), encryptionKey));
        this.totalSavings = Double.parseDouble(encrypt(String.valueOf(this.totalSavings), encryptionKey));
        this.totalDebt = Double.parseDouble(encrypt(String.valueOf(this.totalDebt), encryptionKey));
    }

    public void decryptFields(String encryptionKey) throws Exception {
        this.totalIncome = Double.parseDouble(decrypt(String.valueOf(this.totalIncome), encryptionKey));
        this.totalExpenses = Double.parseDouble(decrypt(String.valueOf(this.totalExpenses), encryptionKey));
        this.deficit = Double.parseDouble(decrypt(String.valueOf(this.deficit), encryptionKey));
        this.remaining = Double.parseDouble(decrypt(String.valueOf(this.remaining), encryptionKey));
        this.totalSavings = Double.parseDouble(decrypt(String.valueOf(this.totalSavings), encryptionKey));
        this.totalDebt = Double.parseDouble(decrypt(String.valueOf(this.totalDebt), encryptionKey));
    }

    // Getters and Setters
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getTotalIncome() {
        return this.totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpenses() {
        return this.totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getDeficit() {
        return this.deficit;
    }

    public void setDeficit(double deficit) {
        this.deficit = deficit;
    }

    public double getRemaining() {
        return this.remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public double getTotalSavings() {
        return this.totalSavings;
    }

    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
    }

    public double getTotalDebt() {
        return this.totalDebt;
    }

    public void setTotalDebt(double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }
}
