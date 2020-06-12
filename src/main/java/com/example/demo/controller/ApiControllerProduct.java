package com.example.demo.controller;

import com.example.demo.entity.ProductsEntity;

import com.example.demo.services.ProductBatchesService;
import com.example.demo.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping(value = "/product")
public class ApiControllerProduct {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductBatchesService productBatchesService;

    @GetMapping(value = "/find",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findProduct(@RequestParam(value = "q", required = false) String q){
        try{
            return ResponseEntity.ok().body(productsService.findByNameLike(q));
        }catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().body(ex);
        }

    }
    @PutMapping(value = "/change/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePrice(@PathVariable("id") Integer id,
                                         @RequestBody ProductsEntity productsEntity){
        try{
            return ResponseEntity.ok().body(productsService.updatePriceProduct(id, productsEntity));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex);
        }
    }
    @GetMapping(value = "/detail/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> productBatchesDetail(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(productBatchesService.findBatchesByProductId(id));
        }catch (EntityNotFoundException ex){
            return ResponseEntity.unprocessableEntity().body(ex);
        }
    }


}
