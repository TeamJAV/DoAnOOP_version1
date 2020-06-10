package com.example.demo.services.Impl;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductService{

    @Autowired
    private ProductRepository productRepository;
    public ProductsEntity save(ProductsEntity productsEntity){return productRepository.save(productsEntity);}
    public List<ProductsEntity> findByNameLike(String q) {
      return productRepository.findByNameContaining(q);
    }
    public ProductsEntity updatePriceProduct(Integer id, ProductsEntity productsEntity){
        ProductsEntity productsSelect = productRepository.findById(id).orElse(null);
        if (productsEntity == null){
            return  null;
        }
        assert productsSelect != null;
        productsSelect.setPrice(productsEntity.getPrice());
        productRepository.save(productsSelect);
        return productsSelect;
    }
}