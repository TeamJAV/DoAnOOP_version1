package com.example.demo.repository;

import com.example.demo.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Integer> {
    List<ProductsEntity> findByNameContaining(String character);
    List<ProductsEntity> findById(int id);
}
