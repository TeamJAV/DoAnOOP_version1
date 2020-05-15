package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "invoice_detail")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private Integer quantity;

    @Column(nullable = false, length = 20)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "sku")
    @JsonIgnoreProperties("invoiceDetail")
    private ProductBatchesEntity productBatches;

    @ManyToOne
    @JoinColumn(name = "refund_invoice_id")
//    @JsonIgnoreProperties("invoiceDetail")
    @JsonIgnoreProperties("invoiceDetail")
    private RefundInvoiceEntity refundInvoice;

    @ManyToOne
    @JoinColumn(name = "selling_invoice")
//    @JsonIgnoreProperties("invoiceDetail")
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


}
