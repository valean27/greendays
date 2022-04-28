package com.greendays.greendays.repository;

import com.greendays.greendays.model.dal.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident,Long> {
}
