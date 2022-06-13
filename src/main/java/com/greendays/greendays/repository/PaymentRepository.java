package com.greendays.greendays.repository;

import com.greendays.greendays.model.dal.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
