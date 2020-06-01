package com.example.demo.repository;

import com.example.demo.entity.InvoiceDetailEntity;
import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.entity.RefundInvoiceEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Integer> {

}
