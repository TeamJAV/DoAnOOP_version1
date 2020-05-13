package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "import_invoice")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class ImportInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_cost", length = 19)
    private Long totalCost;

    @OneToMany(mappedBy = "importInvoice", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("importInvoice")
//    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "importInvoice"})
    private List<ProductBatchesEntity> productBatches = new ArrayList<>();

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public List<ProductBatchesEntity> getProductBatches() {
        return productBatches;
    }

    public void setProductBatches(List<ProductBatchesEntity> productBatches) {
        this.productBatches = productBatches;
    }

    protected ImportInvoiceEntity() {
    }

    public ImportInvoiceEntity(Long totalCost){
        this.totalCost = totalCost;
    }
}
