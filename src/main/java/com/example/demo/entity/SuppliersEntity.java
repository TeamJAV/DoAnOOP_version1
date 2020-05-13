package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Suppliers")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class SuppliersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(name = "phone_number", unique = true, length = 12)
    private String phoneNumber;

    @Column(unique = true, length = 100)
    private String address;

    @OneToMany(mappedBy = "suppliers",cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("suppliers")
//    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "suppliers"})
    private List<ProductBatchesEntity> productBatches = new ArrayList<>();

    protected SuppliersEntity(){}

    public SuppliersEntity(String name, String phoneNumber, String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public List<ProductBatchesEntity> getProductBatches() {
        return productBatches;
    }

    public void setProductBatches(List<ProductBatchesEntity> productBatches) {
        this.productBatches = productBatches;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
