package com.example.demo.repository;

import com.example.demo.entity.ProductBatchesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductBatchesRepository extends JpaRepository<ProductBatchesEntity, String> {
    List<ProductBatchesEntity> findAllByProductsId(Integer products_id);
    @Query(value = "select * from product_batches pb where pb.sku like :sku%", nativeQuery = true)
    List<ProductBatchesEntity> findBySKU(String sku);

}

