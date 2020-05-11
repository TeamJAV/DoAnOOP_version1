package com.example.demo.repository;

import com.example.demo.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductsEntity, Integer> {
}
