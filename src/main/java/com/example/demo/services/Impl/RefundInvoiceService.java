package com.example.demo.services.Impl;


import com.example.demo.entity.RefundInvoiceEntity;
import com.example.demo.repository.RefundInvoiceRepository;
import com.example.demo.services.IRefundInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundInvoiceService implements IRefundInvoiceService {
    @Autowired
    private RefundInvoiceRepository refundInvoiceRepository;

    public void save(RefundInvoiceEntity refundInvoiceEntity){
         refundInvoiceRepository.save(refundInvoiceEntity);
    }
    public List<RefundInvoiceEntity> findALL(){
        return refundInvoiceRepository.findAll();
    }
//    public RefundInvoiceEntity findByID(Integer id){
//        return refundInvoiceRepository.findById(id).orElse(null);
//    }
}
