package com.example.demo.repository;

import com.example.demo.entity.SellingInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface SellingInvoiceRepository extends JpaRepository<SellingInvoiceEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update selling_invoice set total_price = ((select sum(price) from invoice_detail where selling_invoice = :id)"+
                   "-(select sum(products.price*invoice_detail.quantity_refund) from invoice_detail "+
                   "inner join product_batches on invoice_detail.sku = product_batches.sku inner join products on product_batches.products_id = products.id " +
                   "where invoice_detail.selling_invoice = :id))*(1-0.01*discount) " +
                   "where id= :id", nativeQuery = true)
    void UpdateTotalPrice( @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from selling_invoice where date_format(date, \"%Y %m %d\") = date_format(date(now()), \"%Y %m %d\")")
    List<SellingInvoiceEntity> SellingTransToday();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from selling_invoice where yearweek(date, 1) = yearweek(curdate(), 1)")
    List<SellingInvoiceEntity> SellingTransThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from selling_invoice where month(date) = month(current_date)")
    List<SellingInvoiceEntity> SellingTransThisMonth();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select SUM(selling_invoice.total_price) as 'TotalCollect',\n" +
            "       (select sum(total_cost) from import_invoice where  id in (select distinct import_invoice_id from product_batches where date_format(import_date, \"%Y %m %d\") = date_format(date(now()), \"%Y %m %d\"))) as 'TotalPay',\n" +
            "       (SUM(selling_invoice.total_price) -  (select sum(total_cost) from import_invoice where  id in (select distinct import_invoice_id from product_batches where date_format(import_date, \"%Y %m %d\") = date_format(date(now()), \"%Y %m %d\")))) as 'TotalInterest'\n" +
            "from selling_invoice\n")
    List<Map<String, Object>> MoneyToday();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select SUM(selling_invoice.total_price) as 'TotalCollect',\n" +
            "       (select  SUM(total_cost) from import_invoice where yearweek(date, 1) = yearweek(current_date, 1)) as 'TotalPay',\n" +
            "       (SUM(selling_invoice.total_price) -  (select  SUM(total_cost) from import_invoice where yearweek(date, 1) = yearweek(current_date, 1))) as 'TotalInterest'\n" +
            "       from selling_invoice where yearweek(date, 1) = yearweek(current_date, 1)")
    List<Map<String, Object>> MoneyThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select SUM(selling_invoice.total_price) as 'TotalCollect',\n" +
            "       (select  SUM(total_cost) from import_invoice where month(date) = month(current_date )) as 'TotalPay',\n" +
            "       (SUM(selling_invoice.total_price) -  (select  SUM(total_cost) from import_invoice where month(date) = month(current_date ))) as 'TotalInterest'\n" +
            "       from selling_invoice where month(date) = month(current_date)")
    List<Map<String, Object>> MoneyThisMonth();
}
