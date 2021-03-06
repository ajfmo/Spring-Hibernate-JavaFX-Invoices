package com.entity;

import com.utilities.Miscellaneous;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "bought_products")
@DynamicUpdate
public class BoughtProduct extends BaseAbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name ="symbol")
    private String symbol;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "vat_rate", nullable = false)
    private BigDecimal vatRate;

    @Column(name = "discount_percents")
    private int discountPercents;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "net_value", nullable = false)
    private BigDecimal netValue;

    @Column(name = "vat_value", nullable = false)
    private BigDecimal vatValue;

    @Column(name = "gross_value", nullable = false)
    private BigDecimal grossValue;

    @Column(name = "discount_val")
    private BigDecimal discountValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Transient private SimpleObjectProperty<BigDecimal> priceProp;
    @Transient private SimpleObjectProperty<BigDecimal> taxRateProp;
    @Transient private SimpleIntegerProperty quantityProp;
    @Transient private SimpleObjectProperty<BigDecimal> netValProp;
    @Transient private SimpleObjectProperty<BigDecimal> taxValProp;
    @Transient private SimpleIntegerProperty discountProp;
    @Transient private SimpleObjectProperty<BigDecimal> discountValProp;
    @Transient private SimpleObjectProperty<BigDecimal> unmodifiedGrossValProp;
    @Transient private SimpleObjectProperty<BigDecimal> grossValProp;

    public BoughtProduct(String productName, String symbol, String unit, BigDecimal price, BigDecimal vatRate,
                         int discountPercents, BigDecimal discountVal)
    {
        this.productName = productName;
        this.symbol = symbol;
        this.unit = unit;
        this.price = price;
        this.vatRate = vatRate;
        this.discountPercents = discountPercents;
        this.discountValue = discountVal;

        this.priceProp = new SimpleObjectProperty<>(price);
        this.taxRateProp = new SimpleObjectProperty<>(vatRate);
        this.quantityProp = new SimpleIntegerProperty(quantity);
        this.discountProp = new SimpleIntegerProperty(discountPercents);
        this.netValProp = new SimpleObjectProperty<>(netValue != null ? netValue : BigDecimal.ZERO);
        this.taxValProp = new SimpleObjectProperty<>(vatValue != null ? vatValue : BigDecimal.ZERO);
        this.unmodifiedGrossValProp = new SimpleObjectProperty<>(grossValue != null ? grossValue : BigDecimal.ZERO);
        this.grossValProp = new SimpleObjectProperty<>(grossValue != null ? grossValue : BigDecimal.ZERO);
        this.discountValProp = new SimpleObjectProperty<>(discountValue != null ? discountValue : BigDecimal.ZERO);
        this.quantityProp.addListener((observable, oldValue, newValue) -> {
            this.setNetValProp(this.getPriceProp().multiply(new BigDecimal(newValue.intValue())).setScale(2,
                    BigDecimal.ROUND_HALF_DOWN));
            this.setTaxValProp(this.getNetValProp().multiply(this.getTaxRateProp().multiply(Miscellaneous.ONE_HUNDREDTH))
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            this.setUnmodifiedGrossValProp(this.getNetValProp().add(this.getTaxValProp())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            this.setGrossValProp(this.getUnmodifiedGrossValProp());

            if (this.getDiscountProp() > 0)
            {
                this.computeDiscount();
            }
        });

        this.discountProp.addListener((observable, oldValue, newValue) -> computeDiscount());
    }

    // two bought products are the same if their name, symbol, unit, CPU, discount and VAT rate are the same
    // similarity of two bought products is not dependable on quantity or any other field
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoughtProduct that = (BoughtProduct) o;
        return id == that.id &&
                discountPercents == that.discountPercents &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(price, that.price) &&
                Objects.equals(vatRate, that.vatRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, symbol, unit, price, vatRate, discountPercents);
    }

    private void computeDiscount()
    {
        this.setDiscountValProp(this.getUnmodifiedGrossValProp().multiply(new BigDecimal(
                this.getDiscountProp() * 0.01)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        this.setGrossValProp(this.getUnmodifiedGrossValProp().subtract(this.getDiscountValProp()));
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

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    private void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public int getDiscountPercents() {
        return discountPercents;
    }

    private void setDiscountPercents(int discountPercents) {
        this.discountPercents = discountPercents;
    }

    public int getQuantity() {
        return quantity;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    private void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    private void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    private void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getPriceProp() {
        return priceProp.get();
    }

    public void setPriceProp(BigDecimal priceProp) {
        this.priceProp.set(priceProp);
        this.setPrice(priceProp);
    }

    public SimpleObjectProperty<BigDecimal> pricePropProperty() {
        return priceProp;
    }

    public BigDecimal getTaxRateProp() {
        return taxRateProp.get();
    }

    public void setTaxRateProp(BigDecimal taxRateProp) {
        this.taxRateProp.set(taxRateProp);
        this.setVatRate(taxRateProp);
    }

    public SimpleObjectProperty<BigDecimal> taxRatePropProperty() {
        return taxRateProp;
    }

    public int getQuantityProp() {
        return quantityProp.get();
    }

    public void setQuantityProp(int quantityProp) {
        this.quantityProp.set(quantityProp);
        this.setQuantity(quantityProp);
    }

    public SimpleIntegerProperty quantityPropProperty() {
        return quantityProp;
    }

    public BigDecimal getNetValProp() {
        return netValProp.get();
    }

    public void setNetValProp(BigDecimal netValProp) {
        this.netValProp.set(netValProp);
        this.setNetValue(netValProp);
    }

    public SimpleObjectProperty<BigDecimal> netValPropProperty() {
        return netValProp;
    }

    public BigDecimal getTaxValProp() {
        return taxValProp.get();
    }

    public void setTaxValProp(BigDecimal taxValProp) {
        this.taxValProp.set(taxValProp);
        this.setVatValue(taxValProp);
    }

    public SimpleObjectProperty<BigDecimal> taxValPropProperty() {
        return taxValProp;
    }

    public int getDiscountProp() {
        return discountProp.get();
    }

    public void setDiscountProp(int discountProp) {
        this.discountProp.set(discountProp);
        this.setDiscountPercents(discountProp);
    }

    public SimpleIntegerProperty discountPropProperty() {
        return discountProp;
    }

    public BigDecimal getGrossValProp() {
        return grossValProp.get();
    }

    public void setGrossValProp(BigDecimal grossValProp) {
        this.grossValProp.set(grossValProp);
        this.setGrossValue(grossValProp);
    }

    public SimpleObjectProperty<BigDecimal> grossValPropProperty() {
        return grossValProp;
    }

    public BigDecimal getUnmodifiedGrossValProp() {
        return unmodifiedGrossValProp.get();
    }

    public void setUnmodifiedGrossValProp(BigDecimal unmodifiedGrossValProp) {
        this.unmodifiedGrossValProp.set(unmodifiedGrossValProp);
    }

    public SimpleObjectProperty<BigDecimal> unmodifiedGrossValPropProperty() {
        return unmodifiedGrossValProp;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getDiscountValProp() {
        return discountValProp.get();
    }

    public void setDiscountValProp(BigDecimal discountValProp) {
        this.discountValProp.set(discountValProp);
        this.setDiscountValue(discountValProp);
    }

    public SimpleObjectProperty<BigDecimal> discountValPropProperty() {
        return discountValProp;
    }

    //endregion
}
