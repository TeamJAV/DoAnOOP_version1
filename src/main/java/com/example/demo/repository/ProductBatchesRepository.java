package com.example.demo.repository;

import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBatchesRepository extends JpaRepository<ProductBatchesEntity, String> {
    List<ProductBatchesEntity> findAllByProductsId(Integer products_id);
}
