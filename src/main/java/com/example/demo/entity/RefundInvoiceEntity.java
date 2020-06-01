package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "refund_invoice")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String note;

    @OneToMany(mappedBy = "refundInvoice",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("refundInvoice")
    private List<InvoiceDetailEntity> invoiceDetail = new ArrayList<>();

    public RefundInvoiceEntity() {
    }

    public RefundInvoiceEntity(Date date, String note) {
        this.date = date;
        this.note = note;
    }


    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

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
}


