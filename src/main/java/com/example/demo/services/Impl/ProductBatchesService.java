package com.example.demo.services.Impl;

import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.repository.ProductBatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ProductBatchesService{
    @Autowired
    private ProductBatchesRepository productBatchesRepository;

    public List<ProductBatchesEntity> findBatchesByProductId(int id){
        return productBatchesRepository.findAllByProductsId(id);
    }
}
