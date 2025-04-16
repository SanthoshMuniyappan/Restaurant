package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
}
