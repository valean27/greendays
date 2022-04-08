package com.greendays.greendays.repository;

import com.greendays.greendays.model.Garbage;
import org.springframework.data.repository.CrudRepository;

public interface GarbageRepository extends CrudRepository<Garbage,Long> {
}
