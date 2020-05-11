package com.example.demo.repository;

import com.example.demo.entity.ProductBatchesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBatchesRepository extends JpaRepository<ProductBatchesEntity, String> {
//    List<ProductBatchesEntity> findBySku(List<ProductBatchesEntity> sku);
}
