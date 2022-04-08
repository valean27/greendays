package com.greendays.greendays.repository;

import com.greendays.greendays.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IncidentRepository extends JpaRepository<Incident,Long> {
}
