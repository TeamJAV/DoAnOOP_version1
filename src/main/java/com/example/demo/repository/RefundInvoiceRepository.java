package com.example.demo.repository;

import com.example.demo.entity.RefundInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundInvoiceRepository extends JpaRepository<RefundInvoiceEntity, Integer> {
}