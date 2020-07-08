package com.example.demo.repository;

import com.example.demo.entity.RefundInvoiceEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(\n" +
            "    select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "       (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "            from (select sum(quantity_refund*selling_price) as 'RefundMoney', sum(total_cost) as 'ImportMoney'\n" +
            "                  from invoice_detail inner join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id, import_invoice\n" +
            "                  where date_format(ri.date, '%Y-%m-%d') = date_format(date(now()), '%Y-%m-%d') and\n" +
            "                        date_format(import_date, '%Y-%m-%d') = date_format(date(now()), '%Y-%m-%d')) as TablePay) as 'TotalPay'\n" +
            "from selling_invoice\n" +
            "where date_format(selling_invoice.date, '%Y-%m-%d') = date_format(date(now()), '%Y-%m-%d')) as Subtable")
    List<Map<String, Object>> MoneyToday();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(\n" +
            "    select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "       (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "            from (select sum(quantity_refund*selling_price) as 'RefundMoney', sum(total_cost) as 'ImportMoney'\n" +
            "                  from invoice_detail inner join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id, import_invoice\n" +
            "                  where yearweek(ri.date, 1) = yearweek(date(now()), 1) and\n" +
            "                        yearweek(import_date, 1) = yearweek(date(now()), 1)) as TablePay) as 'TotalPay'\n" +
            "from selling_invoice\n" +
            "where yearweek(selling_invoice.date, 1) = yearweek(date(now()), 1)) as Subtable\n")
    List<Map<String, Object>> MoneyThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(\n" +
            "    select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "       (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "            from (select sum(quantity_refund*selling_price) as 'RefundMoney', sum(total_cost) as 'ImportMoney'\n" +
            "                  from invoice_detail inner join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id, import_invoice\n" +
            "                  where month(ri.date) = month(date(now())) and\n" +
            "                        month(import_date) = month(date(now()))) as TablePay) as 'TotalPay'\n" +
            "from selling_invoice\n" +
            "where month(selling_invoice.date) = month(date(now()))) as Subtable")
    List<Map<String, Object>> MoneyThisMonth();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select *\n" +
            "from selling_invoice\n" +
            "where :fromDate  <= selling_invoice.date and selling_invoice.date <= :toDate")
    List<SellingInvoiceEntity> SellingTransSpecificTime(@Param("fromDate") Date fromDate,
                                                      @Param("toDate") Date toDate);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(\n" +
            "        select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "               (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "                from (select sum(quantity_refund*selling_price) as 'RefundMoney', sum(total_cost) as 'ImportMoney'\n" +
            "                      from invoice_detail inner join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id, import_invoice\n" +
            "                        where (:fromDate <= date_format(ri.date, '%Y-%m-%d') and date_format(ri.date, '%Y-%m-%d') <= :toDate) and\n" +
            "                              (:fromDate <= date_format(import_date, '%Y-%m-%d') and date_format(import_date, '%Y-%m-%d') <= :toDate)) as TablePay) as 'TotalPay'\n" +
            "        from selling_invoice\n" +
            "        where :fromDate <= date_format(selling_invoice.date, '%Y-%m-%d') and date_format(selling_invoice.date, '%Y-%m-%d') <= :toDate) as Subtable\n")
    List<Map<String, Object>> MoneyInSpecificTime(@Param("fromDate") Date fromDate,
                                                  @Param("toDate") Date toDate);
}
