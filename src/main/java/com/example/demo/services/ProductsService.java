package com.example.demo.services;

import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    //It's coding time!!!
}