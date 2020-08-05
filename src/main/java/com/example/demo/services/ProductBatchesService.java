package com.example.demo.services;

import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.entity.SuppliersEntity;
import com.example.demo.repository.ProductBatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductBatchesService {

    @Autowired
    private ProductBatchesRepository productBatchesRepository;

    public List<ProductBatchesEntity> findBySKU(String sku) {
        return productBatchesRepository.findBySKU(sku);
    }

    public void updateQuantityBySku (String sku, int quantity) {
        productBatchesRepository.updateQuantityBySku(sku, quantity);
    }

    public ProductBatchesEntity save1(ProductBatchesEntity batch){
        return productBatchesRepository.save(batch);
    }

    public List<ProductBatchesEntity> findAll() {
        return productBatchesRepository.findAll();
    }

    public void save(ProductBatchesEntity batch){
        productBatchesRepository.save(batch);
    }

    public List<ProductBatchesEntity> findBatchesByProductId(int id){
        return productBatchesRepository.findAllByProductsId(id);
    }

    public List<ProductBatchesEntity> getOutOfDateBatches() {
        return productBatchesRepository.getOutOfDateBatches();
    }
}