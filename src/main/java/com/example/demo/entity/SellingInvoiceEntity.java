package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "selling_invoice")
public class SellingInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price", nullable = false, length = 19)
    private Long totalPrice;

    @Column
    private Float discount;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy = "sellingInvoice")
//    @JsonIgnoreProperties("sellingInvoice")
    @JsonIgnoreProperties("sellingInvoice")
    private List<InvoiceDetailEntity> invoiceDetail = new ArrayList<>();

//    @OneToMany(mappedBy = "sellingInvoice")
////    @JsonIgnoreProperties("sellingInvoice")
//    @JsonBackReference
//    private List<RefundInvoiceEntity> refundInvoice = new ArrayList<>();

    public SellingInvoiceEntity(Long totalPrice, Float discount, Date date) {
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.date = date;
    }

    protected SellingInvoiceEntity() {

    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<InvoiceDetailEntity> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetailEntity> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

//    public List<RefundInvoiceEntity> getRefundInvoice() {
//        return refundInvoice;
//    }
//
//    public void setRefundInvoice(List<RefundInvoiceEntity> refundInvoice) {
//        this.refundInvoice = refundInvoice;
//    }

}
