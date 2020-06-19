package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "import_invoice")
public class ImportInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "import_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date importDate;

    @Column(name = "total_cost", length = 19, nullable = true)
    private Long totalCost;

    @JsonIgnoreProperties("importInvoice")
    @OneToMany(mappedBy = "importInvoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public ImportInvoiceEntity() {
    }

    public ImportInvoiceEntity(Long totalCost){
        this.totalCost = totalCost;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

}
