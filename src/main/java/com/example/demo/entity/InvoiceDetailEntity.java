package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "invoice_detail")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicInsert
public class InvoiceDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10, nullable = false)
    private Integer quantity;

    @Column(length = 20, nullable = false)
    private Long price;

    @Column(length = 10)
    @ColumnDefault("0")
    private Integer quantityRefund;

    @Column(nullable = false)
    private Long sellingPrice;

    @ManyToOne
    @JoinColumn(name = "sku")
    @JsonIgnoreProperties("invoiceDetail")
    private ProductBatchesEntity productBatches;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refund_invoice_id")
    @JsonIgnoreProperties("invoiceDetail")
    private RefundInvoiceEntity refundInvoice;

    @ManyToOne
    @JoinColumn(name = "selling_invoice")
    @JsonIgnoreProperties("invoiceDetail")
    private SellingInvoiceEntity sellingInvoice;

    protected InvoiceDetailEntity() {
    }

    public InvoiceDetailEntity(int quantity, Long price){
        this.quantity = quantity;
        this.price = price;

    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

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

    public Integer getQuantityRefund() {
        return quantityRefund;
    }

    public void setQuantityRefund(Integer quantityRefund) {
        this.quantityRefund = quantityRefund;
    }

    public Long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
