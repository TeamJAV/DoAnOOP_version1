package com.example.demo.repository;


import com.example.demo.entity.ImportInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ImportInvoiceRepository extends JpaRepository<ImportInvoiceEntity, Integer> {
    @Modifying
    @Query(value = "UPDATE import_invoice i \n" +
            "INNER JOIN(\n" +
            "   SELECT import_invoice_id, SUM(import_quantity * import_cost) AS \"total\"\n" +
            "   FROM product_batches\n" +
            "   GROUP BY import_invoice_id\n" +
            ") b ON i.id = b.import_invoice_id\n" +
            "SET i.total_cost = b.total", nativeQuery = true)
    void updateTotalCost();

    @Query(value = "select id, total_cost\n" +
            "FROM import_invoice ii,\n" +
            "     (SELECT distinct import_invoice_id, import_date FROM product_batches WHERE import_date = :importDate) pb\n" +
            "WHERE pb.import_invoice_id = id", nativeQuery = true)
    List<ImportInvoiceEntity> findByDate(@Param("importDate") Optional<String> importDate);
}