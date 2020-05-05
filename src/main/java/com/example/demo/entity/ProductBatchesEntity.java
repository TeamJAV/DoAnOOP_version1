package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_batches")
public class ProductBatchesEntity {
    @Id
    @Column(unique = true, nullable = false, length = 12)
    private String sku;

    @Column(name = "import_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importDate;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Column(length = 10)
    private int quantity;

    @Column(name = "import_cost", length = 20)
    private Long importCost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private SuppliersEntity suppliers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "products_id")
    private ProductsEntity products;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "import_Invoice_id")
    private ImportInvoiceEntity importInvoice;

    @OneToMany(mappedBy = "productBatches", cascade = CascadeType.ALL)
    private List<InvoiceDetailEntity> invoiceDetail = new ArrayList<>();

    protected ProductBatchesEntity() {
    }

    public ProductBatchesEntity(Date importDate, Date expiredDate, int quantity, Long importCost) {
        this.importDate = importDate;
        this.expiredDate = expiredDate;
        this.quantity = quantity;
        this.importCost = importCost;
    }

    public SuppliersEntity getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(SuppliersEntity suppliers) {
        this.suppliers = suppliers;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getImportCost() {
        return importCost;
    }

    public void setImportCost(Long importCost) {
        this.importCost = importCost;
    }

    public ProductsEntity getProducts() {
        return products;
    }

    public void setProducts(ProductsEntity products) {
        this.products = products;
    }

    public ImportInvoiceEntity getImportInvoice() {
        return importInvoice;
    }

    public void setImportInvoice(ImportInvoiceEntity importInvoice) {
        this.importInvoice = importInvoice;
    }

    public List<InvoiceDetailEntity> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetailEntity> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }


}
