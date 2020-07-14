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
            "from(select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "               (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "                from (select  sum(import_invoice.total_cost) as 'ImportMoney', Table1.RefundMoney as 'RefundMoney'\n" +
            "                      from  (select sum(quantity_refund*selling_price) as 'RefundMoney'\n" +
            "                             from  invoice_detail  join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id\n" +
            "                             where (date_format(date(now()), '%Y-%m-%d') = date_format(ri.date, '%Y-%m-%d'))) as Table1,\n" +
            "                            import_invoice\n" +
            "                      where  (date_format(date(now()), '%Y-%m-%d')= date_format(import_date, '%Y-%m-%d'))) as TablePay) as 'TotalPay'\n" +
            "        from selling_invoice\n" +
            "        where date_format(date(now()), '%Y-%m-%d') = date_format(selling_invoice.date, '%Y-%m-%d')) as Subtable")
    List<Map<String, Object>> MoneyToday();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "               (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "                from (select  sum(import_invoice.total_cost) as 'ImportMoney', Table1.RefundMoney as 'RefundMoney'\n" +
            "                      from  (select sum(quantity_refund*selling_price) as 'RefundMoney'\n" +
            "                             from  invoice_detail  join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id\n" +
            "                             where (yearweek(date(now()), 1) = yearweek(ri.date, 1))) as Table1,\n" +
            "                            import_invoice\n" +
            "                      where  (yearweek(date(now()), 1)= yearweek(import_date, 1))) as TablePay) as 'TotalPay'\n" +
            "        from selling_invoice\n" +
            "        where yearweek(date(now()), 1) = yearweek(selling_invoice.date, 1)) as Subtable\n")
    List<Map<String, Object>> MoneyThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "               (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "                from (select  sum(import_invoice.total_cost) as 'ImportMoney', Table1.RefundMoney as 'RefundMoney'\n" +
            "                      from  (select sum(quantity_refund*selling_price) as 'RefundMoney'\n" +
            "                             from  invoice_detail  join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id\n" +
            "                             where (month(date(now())) = month(ri.date))) as Table1,\n" +
            "                            import_invoice\n" +
            "                      where  (month(date(now()))= month(import_date))) as TablePay) as 'TotalPay'\n" +
            "        from selling_invoice\n" +
            "        where month(date(now())) = month(selling_invoice.date)) as Subtable\n")
    List<Map<String, Object>> MoneyThisMonth();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select *\n" +
            "from selling_invoice\n" +
            "where date_format(selling_invoice.date, '%Y-%m-%d') between :fromDate and :toDate")
//            "where :fromDate  <= selling_invoice.date and selling_invoice.date <= :toDate")
    List<SellingInvoiceEntity> SellingTransSpecificTime(@Param("fromDate") String fromDate,
                                                      @Param("toDate") String toDate);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select Subtable.TotalPay as 'TotalPay', Subtable.TotalCollect as 'TotalCollect',\n" +
            "       ( Subtable.TotalCollect - Subtable.TotalPay) as 'TotalInterest'\n" +
            "from(\n" +
            "        select sum(selling_invoice.total_price) as 'TotalCollect',\n" +
            "               (select (ImportMoney + RefundMoney) as 'TotalPay'\n" +
            "                from (select  sum(import_invoice.total_cost) as 'ImportMoney', Table1.RefundMoney as 'RefundMoney'\n" +
            "                      from  (select sum(quantity_refund*selling_price) as 'RefundMoney'\n" +
            "                             from  invoice_detail  join refund_invoice ri on invoice_detail.refund_invoice_id = ri.id\n" +
            "                             where ( date_format(ri.date, '%Y-%m-%d') between :fromDate and :toDate)) as Table1,\n" +
            "                            import_invoice\n" +
            "                      where  (date_format(import_date, '%Y-%m-%d') between :fromDate and :toDate) ) as TablePay) as 'TotalPay'\n" +
            "        from selling_invoice\n" +
            "        where date_format(selling_invoice.date, '%Y-%m-%d') between :fromDate and :toDate) as Subtable\n")
    List<Map<String, Object>> MoneyInSpecificTime(@Param("fromDate") String fromDate,
                                                  @Param("toDate") String toDate);
}
