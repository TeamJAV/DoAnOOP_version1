package com.example.demo.services;

import com.example.demo.entity.InvoiceDetailEntity;
import com.example.demo.repository.InvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    @Autowired
    private ProductBatchesService productBatchesService;

    public InvoiceDetailEntity save (InvoiceDetailEntity invoiceDetail) {
        InvoiceDetailEntity ide = invoiceDetailRepository.save(invoiceDetail);
        String sku = ide.getProductBatches().getSku();
        int quantity = ide.getQuantity();
        productBatchesService.updateQuantityBySku(sku, quantity);
        return ide;
    }

    public List<InvoiceDetailEntity> findAll () {
        return invoiceDetailRepository.findAll();
    }
    public InvoiceDetailEntity findById(int id){
        return invoiceDetailRepository.findById(id).orElse(null);
    }

}