package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "invoice_detail")
public class InvoiceDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 10)
    private Integer quantity;

    @Column(nullable = false, length = 20)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "sku")
    private ProductBatchesEntity productBatches;

    @ManyToOne
    @JoinColumn(name = "refund_invoice_id")
    private RefundInvoiceEntity refundInvoice;

    @ManyToOne
    @JoinColumn(name = "selling_invoice")
    private SellingInvoiceEntity sellingInvoice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ProductBatchesEntity getProductBatches() {
        return productBatches;
    }

    public void setProductBatches(ProductBatchesEntity productBatches) {
        this.productBatches = productBatches;
    }

    public RefundInvoiceEntity getRefundInvoice() {
        return refundInvoice;
    }

    public void setRefundInvoice(RefundInvoiceEntity refundInvoice) {
        this.refundInvoice = refundInvoice;
    }

    public SellingInvoiceEntity getSellingInvoice() {
        return sellingInvoice;
    }

    public void setSellingInvoice(SellingInvoiceEntity sellingInvoice) {
        this.sellingInvoice = sellingInvoice;
    }

    public InvoiceDetailEntity() {
    }
}
