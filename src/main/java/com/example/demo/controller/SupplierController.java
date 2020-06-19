package com.example.demo.controller;

import com.example.demo.entity.ProductsEntity;
import com.example.demo.entity.SuppliersEntity;
import com.example.demo.services.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supply")
public class SupplierController {
    @Autowired
    private SuppliersService supService;

    @RequestMapping(value = "supplier/{id}", method = RequestMethod.DELETE)
    public void deleteSupplier(@PathVariable int id){
        supService.deleteSupplier(id);
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.POST)
    public ResponseEntity<?> createSupplier(@RequestBody SuppliersEntity supplier){
        return new ResponseEntity<>(supService.save(supplier), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/all_supplier", method = RequestMethod.GET)
    public ResponseEntity<?> allSupplier(){
        List<SuppliersEntity> suppliers = supService.findAll();
        if(suppliers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @RequestMapping(value = "/supplier/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSupplier(@PathVariable("id") int id, @RequestBody SuppliersEntity supplier) {
        Optional<SuppliersEntity> supData = supService.findById2(id);

        if (supData.isPresent()) {
            SuppliersEntity supp = supData.get();
            supp.setName(supplier.getName());
            supp.setAddress(supplier.getAddress());
            supp.setPhoneNumber(supplier.getPhoneNumber());
            return new ResponseEntity<>(supService.save(supp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

