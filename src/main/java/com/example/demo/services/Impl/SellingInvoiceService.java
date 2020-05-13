package com.example.demo.services.Impl;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.repository.SellingInvoiceRepository;
import com.example.demo.services.ISellingInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellingInvoiceService implements ISellingInvoice {
    @Autowired
    private SellingInvoiceRepository sellingInvoiceRepository;

    public SellingInvoiceEntity findByID(Integer id){ return sellingInvoiceRepository.findById(id).orElse(null);}
    public void save(SellingInvoiceEntity sellingInvoiceEntity){ sellingInvoiceRepository.save(sellingInvoiceEntity);}
    public List<SellingInvoiceEntity> findList(){ return  sellingInvoiceRepository.findAll();}
}
