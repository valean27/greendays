package com.greendays.greendays.repository;

import com.greendays.greendays.model.dal.Garbage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarbageRepository extends JpaRepository<Garbage,Long> {

}
