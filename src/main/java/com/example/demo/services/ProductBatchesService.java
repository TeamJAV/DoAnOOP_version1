package com.example.demo.services;

import com.example.demo.entity.ProductBatchesEntity;
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
}