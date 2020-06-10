package com.example.demo.services;

import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.repository.SellingInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SellingInvoiceService {

    @Autowired
    private SellingInvoiceRepository sellingInvoiceRepository;

    public SellingInvoiceEntity save (SellingInvoiceEntity sellingInvoice) {
        return sellingInvoiceRepository.save(sellingInvoice);
    }

    public List<SellingInvoiceEntity> findAll () {
        return sellingInvoiceRepository.findAll();
    }
}
