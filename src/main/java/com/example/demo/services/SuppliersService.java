package com.example.demo.services;

import com.example.demo.entity.SuppliersEntity;
import com.example.demo.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SuppliersService {

    @Autowired
    private SuppliersRepository suppliersRepository;

    public List<SuppliersEntity> findAll() {
        return suppliersRepository.findAll();
    }
    //It's coding time!!!
}