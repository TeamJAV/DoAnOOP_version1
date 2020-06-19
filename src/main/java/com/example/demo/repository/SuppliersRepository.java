package com.example.demo.repository;

import com.example.demo.entity.SuppliersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuppliersRepository extends JpaRepository<SuppliersEntity, Integer> {
    SuppliersEntity findById(int Id);
    List<SuppliersEntity> findByNameContaining(String name);
    void deleteById(int Id);
}