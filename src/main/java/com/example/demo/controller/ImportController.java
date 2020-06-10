package com.example.demo.controller;

import com.example.demo.entity.ImportInvoiceEntity;
import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ImportInvoiceRepository;
import com.example.demo.services.ImportInvoiceService;
import com.example.demo.services.ProductBatchesService;
import com.example.demo.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/import")
public class ImportController {
    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductBatchesService batchesService;

    @Autowired
    private ImportInvoiceService importService;

    @Autowired
    private ImportInvoiceRepository importRepo;

    Random rd = new Random();

    @RequestMapping("/")
    public String home(){
        return "hello";
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<ProductsEntity>> allProduct(){
        List<ProductsEntity> products = productsService.findAll();
        if(products.isEmpty()){
            return new ResponseEntity<List<ProductsEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ProductsEntity>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<?> createPro(@RequestBody ProductsEntity product){
        return new ResponseEntity<>(productsService.save(product), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ProductsEntity>> getOneProduct (@PathVariable int id) {
        List<ProductsEntity> product = productsService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/invoice")
    public List<ImportInvoiceEntity> allImport(){
        return importService.findAll();
    }

    @PostMapping("/invoice")
    public ResponseEntity<ImportInvoiceEntity> createImport(@RequestBody ImportInvoiceEntity importInvoice) {
        if(importInvoice == null){
            return new ResponseEntity<>(importInvoice, HttpStatus.BAD_REQUEST);
        }else{
            importInvoice.getProductBatches().forEach((batch) -> {
                String sku = "SP" + String.valueOf(rd.nextInt(10000));
                batch.setSku(sku);
                batch.setImportInvoice(importInvoice);
            });
            importService.save2(importInvoice);
            importService.updateTotalCost();
        }
        return new ResponseEntity<>(importInvoice, HttpStatus.OK);
    }
}