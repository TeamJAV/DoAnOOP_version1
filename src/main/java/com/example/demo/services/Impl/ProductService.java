package com.example.demo.services.Impl;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService  implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<ProductsEntity> listProduct(){
        return productRepository.findAll();
    }
    public ProductsEntity findByID(Integer id){
        return productRepository.findById(id).orElse(null);
    }
}
