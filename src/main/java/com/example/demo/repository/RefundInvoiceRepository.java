package com.example.demo.repository;

import com.example.demo.entity.RefundInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RefundInvoiceRepository extends JpaRepository<RefundInvoiceEntity, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from refund_invoice where date_format(date, \"%Y %m %d\") = date_format(date(now()), \"%Y %m %d\")")
    List<RefundInvoiceEntity> RefundTransToday();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from refund_invoice where yearweek(date, 1) = yearweek(curdate(), 1)")
    List<RefundInvoiceEntity> RefundTransThisWeek();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from refund_invoice where month(date) = month(current_date)")
    List<RefundInvoiceEntity> RefundTransThisMonth();
}
