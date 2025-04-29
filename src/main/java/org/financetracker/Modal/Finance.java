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
    private String totalIncome;

    @Column(nullable = false)
    private String totalExpenses;

    @Column(nullable = false)
    private String deficit;

    @Column(nullable = false)
    private String remaining;

    @Column(nullable = false)
    private String totalSavings;

    @Column(nullable = false)
    private String totalDebt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "finance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chart> charts;

    // Constructors
    public Finance() {}

    public Finance(String totalIncome, String totalExpenses, String deficit, String remaining, String totalSavings, String totalDebt) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.deficit = deficit;
        this.remaining = remaining;
        this.totalSavings = totalSavings;
        this.totalDebt = totalDebt;
    }

    // Methods
    public void encryptFields(String encryptionKey) throws Exception {
        this.totalIncome = encrypt(String.valueOf(this.totalIncome), encryptionKey);
        this.totalExpenses = encrypt(String.valueOf(this.totalExpenses), encryptionKey);
        this.deficit = encrypt(String.valueOf(this.deficit), encryptionKey);
        this.remaining = encrypt(String.valueOf(this.remaining), encryptionKey);
        this.totalSavings = encrypt(String.valueOf(this.totalSavings), encryptionKey);
        this.totalDebt = encrypt(String.valueOf(this.totalDebt), encryptionKey);
    }

    public void decryptFields(String encryptionKey) throws Exception {
        this.totalIncome = decrypt(this.totalIncome, encryptionKey);
        this.totalExpenses = decrypt(this.totalExpenses, encryptionKey);
        this.deficit = decrypt(this.deficit, encryptionKey);
        this.remaining = decrypt(this.remaining, encryptionKey);
        this.totalSavings = decrypt(this.totalSavings, encryptionKey);
        this.totalDebt = decrypt(this.totalDebt, encryptionKey);
    }


    // Getters and Setters
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTotalIncome() {
        return this.totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalExpenses() {
        return this.totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getDeficit() {
        return this.deficit;
    }

    public void setDeficit(String deficit) {
        this.deficit = deficit;
    }

    public String getRemaining() {
        return this.remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getTotalSavings() {
        return this.totalSavings;
    }

    public void setTotalSavings(String totalSavings) {
        this.totalSavings = totalSavings;
    }

    public String getTotalDebt() {
        return this.totalDebt;
    }

    public void setTotalDebt(String totalDebt) {
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
