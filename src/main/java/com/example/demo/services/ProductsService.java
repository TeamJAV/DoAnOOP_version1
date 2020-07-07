package com.example.demo.services;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;


    public ProductsEntity save(ProductsEntity product){
        return productsRepository.save(product);
    }

    public List<ProductsEntity> findAll() {
        return productsRepository.findAll();
    }

    public Optional<ProductsEntity> findById(int Id){
        return productsRepository.findById(Id);
    }

    public List<ProductsEntity> findByNameLike(String q) {
        return productsRepository.findByNameContaining(q);
    }
    public ProductsEntity updatePriceProduct(Integer id, ProductsEntity productsEntity){
        ProductsEntity productsSelect = productsRepository.findById(id).orElse(null);
        if (productsEntity == null){
            return  null;
        }
        assert productsSelect != null;
        productsSelect.setPrice(productsEntity.getPrice());
        productsRepository.save(productsSelect);
        return productsSelect;
    }

    public boolean existByName(String name){
        return productsRepository.existsByName(name);
    }

    public boolean existByID(Integer id){
        return productsRepository.existsById(id);
    }
}