package com.example.demo.repository;


import com.example.demo.entity.ImportInvoiceEntity;
import com.example.demo.entity.RefundInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

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

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select DISTINCT import_invoice.*, import_date\n" +
            "from import_invoice\n" +
            "inner join product_batches on import_invoice.id = product_batches.import_invoice_id\n" +
            "where date_format(import_date, \"%Y %m %d\") = date_format(date(now()), \"%Y %m %d\")")
    List<ImportInvoiceEntity> ImportTransToday();
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select DISTINCT import_invoice.*, import_date\n" +
            "from import_invoice\n" +
            "inner join product_batches on import_invoice.id = product_batches.import_invoice_id\n" +
            "where yearweek(import_date, 1) = yearweek(current_date, 1)")
    List<ImportInvoiceEntity> ImportTransThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select DISTINCT import_invoice.*, import_date\n" +
            "from import_invoice\n" +
            "inner join product_batches on import_invoice.id = product_batches.import_invoice_id\n" +
            "where month(import_date) = month(current_date)")
    List<ImportInvoiceEntity> ImportTransThisMonth();

    @org.springframework.transaction.annotation.Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select *\n" +
            "from import_invoice\n" +
            "where :fromDate  <= import_invoice.import_date and import_invoice.import_date <= :toDate")
    List<ImportInvoiceEntity> ImportTransSpecificTime(@Param("fromDate") Date fromDate,
                                                      @Param("toDate") Date toDate);

}