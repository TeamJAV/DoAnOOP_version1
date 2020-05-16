package com.example.demo.services;

import com.example.demo.repository.ImportInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ImportInvoiceService {

    @Autowired
    private ImportInvoiceRepository importInvoiceRepository;

    //It's coding time!!!
}