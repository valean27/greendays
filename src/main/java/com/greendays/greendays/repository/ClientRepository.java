package com.greendays.greendays.repository;

import com.greendays.greendays.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ClientRepository extends CrudRepository<Client,Long> {

}
