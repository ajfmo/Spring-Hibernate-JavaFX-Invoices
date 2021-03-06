package com.entity;

import com.enums.InvoiceStatus;
import com.enums.InvoiceType;
import com.enums.PaymentMethod;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issued_invoices")
@DynamicUpdate
public class Invoice extends BaseAbstractEntity
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "invoice_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "net_value", nullable = false)
    private BigDecimal netValue;

    @Column(name = "vat_value", nullable = false)
    private BigDecimal vatValue;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "gross_value", nullable = false)
    private BigDecimal grossValue;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    // TODO: check in code for expiration date
    @Column(name = "payment_date_days", nullable = false)
    private Integer paymentDateDays;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "last_modified", nullable = false)
    private LocalDate lastModified;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "notes")
    private String remarks;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BoughtProduct> boughtProductSet;

    // TODO: add cascade types
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    private Invoice()
    {
        // TODO: initialize invoice number, currency from settings
        this.issueDate = LocalDate.now();
        this.creationDate = LocalDate.now();
        this.saleDate = LocalDate.now();
        this.lastModified = LocalDate.now();
        this.netValue = BigDecimal.ZERO;
        this.vatValue = BigDecimal.ZERO;
        this.discountValue = BigDecimal.ZERO;
        this.grossValue = BigDecimal.ZERO;
        this.paidAmount = BigDecimal.ZERO;
        this.boughtProductSet = new HashSet<>();
        this.status = InvoiceStatus.ISSUED;
    }

    public Invoice(InvoiceType type)
    {
        this();
        this.type = type;
    }

    public Invoice(Customer customer)
    {
        this();
        this.customer = customer;
    }

    //region getters and setters
    public int getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public InvoiceType getType() {
        return type;
    }

    public Seller getSeller() {
        return seller;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public Integer getPaymentDateDays() {
        return paymentDateDays;
    }

    public String getCurrency() {
        return currency;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public Set<BoughtProduct> getBoughtProductSet() {
        return boughtProductSet;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getLocation() {
        return location;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public void setPaymentDateDays(Integer paymentDateDays) {
        this.paymentDateDays = paymentDateDays;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setBoughtProductSet(Set<BoughtProduct> boughtProductSet) {
        this.boughtProductSet = boughtProductSet;
    }
    //endregion
}
