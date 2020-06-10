package com.example.demo.services.Impl;

import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.repository.SellingInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SellingInvoiceService{
    @Autowired
    private SellingInvoiceRepository sellingInvoiceRepository;

    public SellingInvoiceEntity findByID(Integer id){ return sellingInvoiceRepository.findById(id).orElse(null);}
    public void save(SellingInvoiceEntity sellingInvoiceEntity){ sellingInvoiceRepository.save(sellingInvoiceEntity);}
    public List<SellingInvoiceEntity> findList(){ return  sellingInvoiceRepository.findAll();}
    public void updateTotalPrice(Integer id){ sellingInvoiceRepository.UpdateTotalPrice(id);}

    public List<SellingInvoiceEntity> SellingTransToday(){return sellingInvoiceRepository.SellingTransToday();}
    public List<SellingInvoiceEntity> SellingTransThisWeek(){return sellingInvoiceRepository.SellingTransThisWeek();}
    public List<SellingInvoiceEntity> SellingTransThisMonth(){return sellingInvoiceRepository.SellingTransThisMonth();}

    public List<Map<String, Object>> RevenueToday(){ return sellingInvoiceRepository.MoneyToday();}
    public List<Map<String, Object>> RevenueThisWeek(){ return sellingInvoiceRepository.MoneyThisWeek();}
    public List<Map<String, Object>> RevenueThisMonth(){ return sellingInvoiceRepository.MoneyThisMonth();}
}
