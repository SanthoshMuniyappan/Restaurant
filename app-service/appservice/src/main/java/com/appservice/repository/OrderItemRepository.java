package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.OrderItems;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, String> {
}
