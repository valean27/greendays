package com.greendays.greendays.repository;

import com.greendays.greendays.model.Incident;
import org.springframework.data.repository.CrudRepository;

public interface IncidentRepository extends CrudRepository<Incident,Long> {
}
