package com.example.demo.services.Impl;

import com.example.demo.entity.InvoiceDetailEntity;
import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.repository.InvoiceDetailRepository;
import com.example.demo.services.IInvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvoiceDetailService implements IInvoiceDetailService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    public List<InvoiceDetailEntity> findInvoiceDetailEntityList(SellingInvoiceEntity sellingInvoiceEntity){
        return invoiceDetailRepository.findAllBySellingInvoice(sellingInvoiceEntity);
    }
    public List<InvoiceDetailEntity> findByProductBatches(ProductBatchesEntity productBatchesEntity){
        return invoiceDetailRepository.findByProductBatches(productBatchesEntity);
    }
}
