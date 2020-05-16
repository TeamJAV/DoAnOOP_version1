package com.example.demo.services;

import com.example.demo.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SuppliersService {

    @Autowired
    private SuppliersRepository suppliersRepository;

    //It's coding time!!!
}