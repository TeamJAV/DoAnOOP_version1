package com.example.demo.controller;

import com.example.demo.entity.SuppliersEntity;
import com.example.demo.services.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SuppliersService supService;

    @RequestMapping("supplier/{id}")
    public void deleteSupplier(@PathVariable int id){
        supService.deleteSupplier(id);
    }
}
