package com.example.demo.repository;


import com.example.demo.entity.ImportInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportInvoiceRepository extends JpaRepository<ImportInvoiceEntity, Integer> {
}