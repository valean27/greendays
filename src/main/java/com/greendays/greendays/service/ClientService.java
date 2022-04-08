package com.greendays.greendays.service;

import com.greendays.greendays.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<String> getClientTypes() {
//return clientRepository.
        return null;
    }
}
