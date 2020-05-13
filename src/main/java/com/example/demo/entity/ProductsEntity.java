package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( unique = true, length = 100)
    private  String name;

    @Column(length = 19)
    private Long price;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("products")
//    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
    private List<ProductBatchesEntity> productBatches = new ArrayList<>();

    protected ProductsEntity() {
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
