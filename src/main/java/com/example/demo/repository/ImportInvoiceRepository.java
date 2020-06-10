package com.example.demo.repository;


import com.example.demo.entity.ImportInvoiceEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImportInvoiceRepository extends JpaRepository<ImportInvoiceEntity, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select import_invoice.*, import_date\n" +
            "from import_invoice\n" +
            "inner join product_batches on import_invoice.id = product_batches.import_invoice_id\n" +
            "where date_format(import_date, \"%Y %m %d\") = date_format(date(now()), \"%Y %m %d\")")
    List<ImportInvoiceEntity> ImportTransToday();
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select import_invoice.*, import_date\n" +
            "from import_invoice\n" +
            "inner join product_batches on import_invoice.id = product_batches.import_invoice_id\n" +
            "where yearweek(import_date, 1) = yearweek(current_date, 1)")
    List<ImportInvoiceEntity> ImportTransThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select import_invoice.*, import_date\n" +
            "from import_invoice\n" +
            "inner join product_batches on import_invoice.id = product_batches.import_invoice_id\n" +
            "where month(import_date) = month(current_date)")
    List<ImportInvoiceEntity> ImportTransThisMonth();
}