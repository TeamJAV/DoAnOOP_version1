package com.example.demo.controller;

import com.example.demo.entity.ProductBatchesEntity;
import com.example.demo.services.ProductBatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellingController {

    @Autowired
    private ProductBatchesService productBatchesService;

    @RequestMapping(
            value = "/find-sku/{sku}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductBatchesEntity>> viewListProducts (@PathVariable String sku) {
        List<ProductBatchesEntity> listProductBatches = productBatchesService.findBySKU(sku);
        return new ResponseEntity<>(listProductBatches, HttpStatus.OK);
    }

}
