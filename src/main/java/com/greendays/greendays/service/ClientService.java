package com.greendays.greendays.service;

import com.greendays.greendays.model.dal.Client;
import com.greendays.greendays.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void insertClient(String clientType){
        Client client = new Client();
        client.setClientType(clientType);
        clientRepository.save(client);
    }
}
