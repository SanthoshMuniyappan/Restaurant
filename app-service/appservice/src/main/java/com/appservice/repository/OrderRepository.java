package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
