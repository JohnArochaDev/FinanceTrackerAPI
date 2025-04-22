package org.financetracker.Modal;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;

import jakarta.persistence.*;
import org.financetracker.Util.ChartType;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Chart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ElementCollection
    private List<String> labels;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Persist as a string in the database
    private ChartType type;

    @OneToMany(mappedBy = "chart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dataset> datasets; // A chart can have many datasets

    @ManyToOne
    @JoinColumn(name = "finance_id", nullable = false)
    private Finance finance;

    private static final Set<ChartType> VALID_TYPES = Set.of(
        ChartType.BAR_DATA,
        ChartType.PIE_DATA,
        ChartType.RADAR_DATA
    );

    // Methods
    public void setType(ChartType type) {
        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid chart type: " + type);
        }
        this.type = type;
    }

    public void encryptLabels(String encryptionKey) {
        this.labels = this.labels.stream()
            .map(label -> {
                try {
                    return encrypt(label, encryptionKey); // Encrypt each label
                } catch (Exception e) {
                    throw new RuntimeException("Failed to encrypt label: " + label, e);
                }
            })
            .collect(Collectors.toList());
    }

    public void decryptLabels(String encryptionKey) {
        this.labels = this.labels.stream()
            .map(label -> {
                try {
                    return decrypt(label, encryptionKey); // Decrypt each label
                } catch (Exception e) {
                    throw new RuntimeException("Failed to decrypt label: " + label, e);
                }
            })
            .collect(Collectors.toList());
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public ChartType getType() {
        return type;
    }
}