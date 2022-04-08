package com.greendays.greendays.repository;

import com.greendays.greendays.model.Garbage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GarbageRepository extends JpaRepository<Garbage,Long> {
}
