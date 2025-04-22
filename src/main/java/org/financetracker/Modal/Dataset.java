package org.financetracker.Modal;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String label;

    @ElementCollection
    private List<Double> data;

    @ElementCollection
    private List<String> backgroundColor;

    @ElementCollection
    private List<String> borderColor;

    @Column(nullable = false)
    private int borderWidth;

    @ManyToOne
    @JoinColumn(name = "chart_id", nullable = false)
    private Chart chart; // Many datasets can belong to one chart

    // Methods
    public void encryptFields(String encryptionKey) {
        try {
            this.label = encrypt(this.label, encryptionKey);
            this.data = this.data.stream()
                .map(value -> {
                    try {
                        return Double.parseDouble(encrypt(String.valueOf(value), encryptionKey));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
            this.backgroundColor = this.backgroundColor.stream()
                .map(color -> {
                    try {
                        return encrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
            this.borderColor = this.borderColor.stream()
                .map(color -> {
                    try {
                        return encrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt Dataset fields", e);
        }
    }

    // Decrypt all fields
    public void decryptFields(String encryptionKey) {
        try {
            this.label = decrypt(this.label, encryptionKey);
            this.data = this.data.stream()
                .map(value -> {
                    try {
                        return Double.parseDouble(decrypt(String.valueOf(value), encryptionKey));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
            this.backgroundColor = this.backgroundColor.stream()
                .map(color -> {
                    try {
                        return decrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
            this.borderColor = this.borderColor.stream()
                .map(color -> {
                    try {
                        return decrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt Dataset fields", e);
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public List<String> getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(List<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<String> getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(List<String> borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }
}