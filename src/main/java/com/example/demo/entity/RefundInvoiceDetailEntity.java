package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "refund_invoice_detail_entity")
public class RefundInvoiceDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10)
    private Integer quantity;

    @Column(length = 20)
    private Long price;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku")
//    @JsonIgnoreProperties("invoiceDetail")
//    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "invoiceDetail"})
    private ProductBatchesEntity productBatches;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refund_invoice_id")
//    @JsonIgnoreProperties("invoiceDetail")
//    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "invoiceDetail"})
    private RefundInvoiceEntity refundInvoice;


    protected RefundInvoiceDetailEntity() {
    }

    public RefundInvoiceDetailEntity(int quantity, Long price){
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

    public void setId(Integer id) {
        this.id = id;
    }
}

