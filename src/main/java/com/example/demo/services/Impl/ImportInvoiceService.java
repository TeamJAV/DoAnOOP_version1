package com.example.demo.services.Impl;

import com.example.demo.entity.ImportInvoiceEntity;
import com.example.demo.repository.ImportInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImportInvoiceService {
    @Autowired
    private ImportInvoiceRepository importInvoiceRepository;

    public List<ImportInvoiceEntity> ImportTransToday(){return importInvoiceRepository.ImportTransToday();}
    public List<ImportInvoiceEntity> ImportTransThisWeek(){return importInvoiceRepository.ImportTransThisWeek();}
    public List<ImportInvoiceEntity> ImportTransThisMonth(){return importInvoiceRepository.ImportTransThisMonth();}
}
