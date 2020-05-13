package com.example.demo.controller;

import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.services.Impl.SellingInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiControllerRefund {
    @Autowired
    private SellingInvoiceService sellingInvoiceService;

    @GetMapping(value = "/selling_invoice",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SellingInvoiceEntity>> findSelling(){
        List<SellingInvoiceEntity> sellingInvoiceEntityList = sellingInvoiceService.findList();
        return new ResponseEntity<>(sellingInvoiceEntityList, HttpStatus.OK);
    }
    @GetMapping(value = "/selling_invoice/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public  SellingInvoiceEntity findByID(@PathVariable("id") Integer id){
        return sellingInvoiceService.findByID(id);
    }

}
