package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RefundInvoiceService {

    @Autowired
    private RefundInvoiceService refundInvoiceRepository;

    //It's coding time!!!
}