package com.example.demo.services;

import com.example.demo.repository.RefundInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RefundInvoiceService {

    @Autowired
    private RefundInvoiceRepository refundInvoiceRepository;

    //It's coding time!!!
}