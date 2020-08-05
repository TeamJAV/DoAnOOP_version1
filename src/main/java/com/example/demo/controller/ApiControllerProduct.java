package com.example.demo.controller;

import com.example.demo.entity.ProductsEntity;

import com.example.demo.entity.SuppliersEntity;
import com.example.demo.services.ProductBatchesService;
import com.example.demo.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


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
    @PostMapping(value = "/change/{id}",
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

    @RequestMapping(value="change/{id}", method=RequestMethod.PUT)
    public ResponseEntity<?> changePrice(@PathVariable("id") int id, @RequestBody ProductsEntity product){
        Optional<ProductsEntity> pro = productsService.findById(id);

        if (pro.isPresent()) {
            ProductsEntity new_pro = pro.get();
            new_pro.setPrice(product.getPrice());
            return new ResponseEntity<>(productsService.save(new_pro), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/outofdate", method = RequestMethod.GET)
    public ResponseEntity<?> getOutOfDateBatches() {
        return new ResponseEntity<>(productBatchesService.getOutOfDateBatches(), HttpStatus.OK);
    }
}
