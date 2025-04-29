package org.financetracker.Modal;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.UUID;
import java.util.stream.Stream;

@Entity
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private double[] data;

    @Column(nullable = false)
    private String[] backgroundColor;

    @Column(nullable = false)
    private String[] borderColor;

    @Column(nullable = false)
    private int borderWidth;

    @ManyToOne
    @JoinColumn(name = "chart_id", nullable = false)
    @JsonBackReference
    private Chart chart;

    // Methods
    public void encryptFields(String encryptionKey) {
        try {
            this.label = encrypt(this.label, encryptionKey);
            this.data = Stream.of(this.data)
                .mapToDouble(value -> {
                    try {
                        return Double.parseDouble(encrypt(String.valueOf(value), encryptionKey));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
            this.backgroundColor = Stream.of(this.backgroundColor)
                .map(color -> {
                    try {
                        return encrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(String[]::new);
            this.borderColor = Stream.of(this.borderColor)
                .map(color -> {
                    try {
                        return encrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(String[]::new);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt Dataset fields", e);
        }
    }

    // Decrypt all fields
    public void decryptFields(String encryptionKey) {
        try {
            this.label = decrypt(this.label, encryptionKey);
            this.data = Stream.of(this.data)
                .mapToDouble(value -> {
                    try {
                        return Double.parseDouble(decrypt(String.valueOf(value), encryptionKey));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
            this.backgroundColor = Stream.of(this.backgroundColor)
                .map(color -> {
                    try {
                        return decrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(String[]::new);
            this.borderColor = Stream.of(this.borderColor)
                .map(color -> {
                    try {
                        return decrypt(color, encryptionKey);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(String[]::new);
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

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public String[] getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String[] backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String[] getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String[] borderColor) {
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
