package com.example.demo.controller;

import com.example.demo.entity.InvoiceDetailEntity;
import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.services.InvoiceDetailService;
import com.example.demo.services.ProductBatchesService;
import com.example.demo.services.SellingInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SellingController {

    @Autowired
    private ProductBatchesService productBatchesService;
    @Autowired
    private SellingInvoiceService sellingInvoiceService;
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @RequestMapping(
            value = "/find-sku/{sku}",
            method = RequestMethod.GET)
    public ResponseEntity<List<ProductBatchesEntity>> viewListProducts (@PathVariable String sku) {
        List<ProductBatchesEntity> listProductBatches = productBatchesService.findBySKU(sku);
        return new ResponseEntity<>(listProductBatches, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/saveSellingInvoice",
            method = RequestMethod.POST)
    public ResponseEntity<SellingInvoiceEntity> saveSellingInvoice (@RequestBody SellingInvoiceEntity reqSellingInvoice) {
        SellingInvoiceEntity resSellingInvoice = sellingInvoiceService.save(reqSellingInvoice);
        return new ResponseEntity<>(resSellingInvoice, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/saveInvoiceDetail",
            method = RequestMethod.POST)
    public ResponseEntity<List<InvoiceDetailEntity>> saveInvoiceDetail (@RequestBody List<InvoiceDetailEntity> reqInvoiceDetail) {

        List<InvoiceDetailEntity> resInvoiceDetail = new ArrayList<>();

        if (reqInvoiceDetail == null) {
            return new ResponseEntity<>(resInvoiceDetail, HttpStatus.BAD_REQUEST);
        }
        for (InvoiceDetailEntity E : reqInvoiceDetail) {
            InvoiceDetailEntity e = invoiceDetailService.save((E));
            resInvoiceDetail.add(e);
        }
        return new ResponseEntity<>(resInvoiceDetail, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/findAllDetail",
            method = RequestMethod.GET)
    public ResponseEntity<List<InvoiceDetailEntity>> findAllDetail () {
        return new ResponseEntity<>(invoiceDetailService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/findAllInvoice",
            method = RequestMethod.GET)
    public ResponseEntity<List<SellingInvoiceEntity>> findAllInvoice () {
        return new ResponseEntity<>(sellingInvoiceService.findAll(), HttpStatus.OK);
    }
}
