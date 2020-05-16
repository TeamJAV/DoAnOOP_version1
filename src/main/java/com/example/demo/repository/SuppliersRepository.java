package com.example.demo.repository;

import com.example.demo.entity.SuppliersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<SuppliersEntity, Integer> {
}