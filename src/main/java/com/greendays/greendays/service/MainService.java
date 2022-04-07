package com.greendays.greendays.service;

import com.greendays.greendays.repository.MainRepository;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private MainRepository repository;

    public MainService(MainRepository repository) {
        this.repository = repository;
    }

}
