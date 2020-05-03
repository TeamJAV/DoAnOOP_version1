package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "selling_invoice")
public class SellingInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "total_price", nullable = false, length = 19)
    private Long totalPrice;

    private Float discount;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "sellingInvoice")
    private List<InvoiceDetailEntity> invoiceDetail = new ArrayList<>();

    @OneToMany(mappedBy = "sellingInvoice")
    private List<RefundInvoiceEntity> refundInvoice = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<InvoiceDetailEntity> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetailEntity> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public List<RefundInvoiceEntity> getRefundInvoice() {
        return refundInvoice;
    }

    public void setRefundInvoice(List<RefundInvoiceEntity> refundInvoice) {
        this.refundInvoice = refundInvoice;
    }

    public SellingInvoiceEntity() {
    }
}
