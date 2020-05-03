package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "refund_invoice")
public class RefundInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    private String note;

    @OneToMany(mappedBy = "refundInvoice")
    private List<InvoiceDetailEntity> invoiceDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "selling_invoice_id")
    private SellingInvoiceEntity sellingInvoice = new SellingInvoiceEntity();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<InvoiceDetailEntity> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetailEntity> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public SellingInvoiceEntity getSellingInvoice() {
        return sellingInvoice;
    }

    public void setSellingInvoice(SellingInvoiceEntity sellingInvoice) {
        this.sellingInvoice = sellingInvoice;
    }

    public RefundInvoiceEntity() {
    }
}
