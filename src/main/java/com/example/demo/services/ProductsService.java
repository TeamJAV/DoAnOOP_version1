package com.example.demo.services;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ProductRepository;
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

    @Autowired
    private ProductRepository productRepository;

    public ProductsEntity save(ProductsEntity product){
        return productRepo.save(product);
    }

    public List<ProductsEntity> findAll() {
        return productRepo.findAll();
    }

    public List<ProductsEntity> findById(int Id){
        return productRepo.findById(Id);
    }

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