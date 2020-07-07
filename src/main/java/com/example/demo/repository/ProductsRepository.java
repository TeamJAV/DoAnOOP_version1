package com.example.demo.repository;

import com.example.demo.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {
    List<ProductsEntity> findByNameContaining(String character);
    Optional<ProductsEntity> findById(int Id);

    boolean existsByName(String name);

    @Override
    boolean existsById(Integer integer);
}