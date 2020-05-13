package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "invoice_detail")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class InvoiceDetailEntity {
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

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "refund_invoice_id")
////    @JsonIgnoreProperties("invoiceDetail")
////    @JsonManagedReference
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "invoiceDetail"})
//    private RefundInvoiceEntity refundInvoice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "selling_invoice")
//    @JsonIgnoreProperties("invoiceDetail")
//    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "invoiceDetail"})
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

//    public RefundInvoiceEntity getRefundInvoice() {
//        return refundInvoice;
//    }
//
//    public void setRefundInvoice(RefundInvoiceEntity refundInvoice) {
//        this.refundInvoice = refundInvoice;
//    }

    public SellingInvoiceEntity getSellingInvoice() {
        return sellingInvoice;
    }

    public void setSellingInvoice(SellingInvoiceEntity sellingInvoice) {
        this.sellingInvoice = sellingInvoice;
    }


}
