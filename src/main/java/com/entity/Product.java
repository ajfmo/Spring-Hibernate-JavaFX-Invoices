package com.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@DynamicUpdate
public class Product extends BaseAbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "tag")
    private String tag;

    @Column(name = "net_price", nullable = false)
    private BigDecimal netPrice;

    @Column(name = "gross_price", nullable = false)
    private BigDecimal grossPrice;

    @Column(name = "vat_rate", nullable = false)
    private BigDecimal vatRate;

    @Column(name = "online_sale", nullable = false)
    private boolean onlineSale;

    @Column(name = "is_service", nullable = false)
    private boolean isService;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @Column(name = "last_modified", nullable = false)
    private LocalDate lastModified;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_item_id", referencedColumnName = "id")
    private Warehouse warehouse;

    public Product() {
        this.creationDate = LocalDate.now();
    }

    public void setAll(String productName, String symbol, String unit, String tag, BigDecimal netPrice,
                   BigDecimal grossPrice, BigDecimal vatRate, boolean onlineSale, boolean isService, boolean isActive,
                   LocalDate lastModified) {
        this.productName = productName;
        this.symbol = symbol;
        this.unit = unit;
        this.tag = tag;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
        this.vatRate = vatRate;
        this.onlineSale = onlineSale;
        this.isService = isService;
        this.isActive = isActive;
        this.lastModified = lastModified;
    }

    //region getters and setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public boolean isOnlineSale() {
        return onlineSale;
    }

    public void setOnlineSale(boolean onlineSale) {
        this.onlineSale = onlineSale;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
    //endregion
}
