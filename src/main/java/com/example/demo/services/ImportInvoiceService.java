package com.example.demo.services;

import com.example.demo.entity.ImportInvoiceEntity;
import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.repository.ImportInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ImportInvoiceService {

    @Autowired
    private ImportInvoiceRepository importInvoiceRepository;

    public List<ImportInvoiceEntity> findAll() {
        return importInvoiceRepository.findAll();
    }

    public ImportInvoiceEntity save2(ImportInvoiceEntity importInvoice){
        return importInvoiceRepository.save(importInvoice);
    }

    public void save(ImportInvoiceEntity importInvoice) {
        importInvoiceRepository.save(importInvoice);
    }

    public void updateTotalCost(){
        importInvoiceRepository.updateTotalCost();
    }
    //It's coding time!!!

    public List<ImportInvoiceEntity> ImportTransToday(){return importInvoiceRepository.ImportTransToday();}
    public List<ImportInvoiceEntity> ImportTransThisWeek(){return importInvoiceRepository.ImportTransThisWeek();}
    public List<ImportInvoiceEntity> ImportTransThisMonth(){return importInvoiceRepository.ImportTransThisMonth();}
}