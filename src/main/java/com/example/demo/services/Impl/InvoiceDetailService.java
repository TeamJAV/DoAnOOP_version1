package com.example.demo.services.Impl;

import com.example.demo.entity.InvoiceDetailEntity;
import com.example.demo.repository.InvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceDetailService{
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    public void save(InvoiceDetailEntity invoiceDetailEntity){
        invoiceDetailRepository.save(invoiceDetailEntity);
    }
    public InvoiceDetailEntity findById(int id){
        return invoiceDetailRepository.findById(id).orElse(null);
    }
}
