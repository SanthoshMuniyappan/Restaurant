package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
}
