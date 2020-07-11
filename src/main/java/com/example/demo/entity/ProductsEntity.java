package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( unique = true, length = 100, nullable = false)
    private  String name;

    @Column(length = 19, nullable = false)
    private Long price;

    @OneToMany(mappedBy = "products")
    @JsonIgnoreProperties("products")
    private List<ProductBatchesEntity> productBatches = new ArrayList<>();

    public ProductsEntity() {
    }

    public ProductsEntity(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<ProductBatchesEntity> getProductBatches() {
        return productBatches;
    }

    public void setProductBatches(List<ProductBatchesEntity> productBatches) {
        this.productBatches = productBatches;
    }

}
