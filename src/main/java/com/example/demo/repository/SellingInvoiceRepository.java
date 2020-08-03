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
    @Query(nativeQuery = true, value =
            "select \n" +
            "   ts.totalSelling as TotalCollect, \n" +
            "   (ti.totalImport + tr.totalRefund) as TotalPay, \n" +
            "   (ts.totalSelling - ti.totalImport - tr.totalRefund) as TotalInterest\n" +
            "from\n" +
            "   (select sum(total_price) as totalSelling, 'dummy_id' AS rn\n" +
            "   from selling_invoice\n" +
            "   where date_format(date, '%Y-%m-%d') = date_format(date(now()), '%Y-%m-%d')) as ts\n" +
            "   inner join\n" +
            "   (select sum(total_cost) as totalImport, 'dummy_id' AS rn\n" +
            "   from import_invoice\n" +
            "   where date_format(import_date, '%Y-%m-%d') = date_format(date(now()), '%Y-%m-%d')) as ti on ts.rn = ti.rn\n" +
            "   inner join\n" +
            "   (select sum(i.quantity_refund * i.selling_price) as totalRefund, 'dummy_id' AS rn\n" +
            "   from invoice_detail as i inner join selling_invoice as s on i.selling_invoice = s.id\n" +
            "   where date_format(s.date, '%Y-%m-%d') = date_format(date(now()), '%Y-%m-%d')) as tr on ts.rn = tr.rn")
    List<Map<String, Object>> MoneyToday();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value =
            "select \n" +
            "   ts.totalSelling as TotalCollect, \n" +
            "   (ti.totalImport + tr.totalRefund) as TotalPay, \n" +
            "   (ts.totalSelling - ti.totalImport - tr.totalRefund) as TotalInterest\n" +
            "from\n" +
            "   (select sum(total_price) as totalSelling, 'dummy_id' AS rn\n" +
            "   from selling_invoice\n" +
            "   where yearweek(date, 1) = yearweek(date(now()), 1)) as ts\n" +
            "   inner join\n" +
            "   (select sum(total_cost) as totalImport, 'dummy_id' AS rn\n" +
            "   from import_invoice\n" +
            "   where yearweek(import_date, 1) = yearweek(date(now()), 1)) as ti on ts.rn = ti.rn\n" +
            "   inner join\n" +
            "   (select sum(i.quantity_refund * i.selling_price) as totalRefund, 'dummy_id' AS rn\n" +
            "   from invoice_detail as i inner join selling_invoice as s on i.selling_invoice = s.id\n" +
            "   where yearweek(s.date, 1) = yearweek(date(now()), 1)) as tr on ts.rn = tr.rn")
    List<Map<String, Object>> MoneyThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value =
            "select \n" +
            "   ts.totalSelling as TotalCollect, \n" +
            "   (ti.totalImport + tr.totalRefund) as TotalPay, \n" +
            "   (ts.totalSelling - ti.totalImport - tr.totalRefund) as TotalInterest\n" +
            "from\n" +
            "   (select sum(total_price) as totalSelling, 'dummy_id' AS rn\n" +
            "   from selling_invoice\n" +
            "   where month(date) = month(date(now()))) as ts\n" +
            "   inner join\n" +
            "   (select sum(total_cost) as totalImport, 'dummy_id' AS rn\n" +
            "   from import_invoice\n" +
            "   where month(import_date) = month(date(now()))) as ti on ts.rn = ti.rn\n" +
            "   inner join\n" +
            "   (select sum(i.quantity_refund * i.selling_price) as totalRefund, 'dummy_id' AS rn\n" +
            "   from invoice_detail as i inner join selling_invoice as s on i.selling_invoice = s.id\n" +
            "   where month(s.date) = month(date(now()))) as tr on ts.rn = tr.rn")
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
    @Query(nativeQuery = true, value =
            "select \n" +
            "   ts.totalSelling as TotalCollect, \n" +
            "   (ti.totalImport + tr.totalRefund) as TotalPay, \n" +
            "   (ts.totalSelling - ti.totalImport - tr.totalRefund) as TotalInterest\n" +
            "from\n" +
            "   (select sum(total_price) as totalSelling, 'dummy_id' AS rn\n" +
            "   from selling_invoice\n" +
            "   where date_format(date, '%Y-%m-%d') between :fromDate and :toDate) as ts\n" +
            "   inner join\n" +
            "   (select sum(total_cost) as totalImport, 'dummy_id' AS rn\n" +
            "   from import_invoice\n" +
            "   where date_format(import_date, '%Y-%m-%d') between :fromDate and :toDate) as ti on ts.rn = ti.rn\n" +
            "   inner join\n" +
            "   (select sum(i.quantity_refund * i.selling_price) as totalRefund, 'dummy_id' AS rn\n" +
            "   from invoice_detail as i inner join selling_invoice as s on i.selling_invoice = s.id\n" +
            "   where date_format(s.date, '%Y-%m-%d') between :fromDate and :toDate) as tr on ts.rn = tr.rn")
    List<Map<String, Object>> MoneyInSpecificTime(@Param("fromDate") String fromDate,
                                                  @Param("toDate") String toDate);
}
