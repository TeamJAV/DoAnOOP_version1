package com.example.demo.services;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductsService {

    @Autowired
    private ProductsRepository productRepo;

    public ProductsEntity save(ProductsEntity product){
        return productRepo.save(product);
    }

    public List<ProductsEntity> findAll() {
        return productRepo.findAll();
    }

    public List<ProductsEntity> findById(int Id){
        return productRepo.findById(Id);
    }


    //It's coding time!!!
}