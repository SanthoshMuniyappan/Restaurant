package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ors.common.model.OrderItems;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, String> {

    @Query(nativeQuery = true,value = "select * from order_items where order_id=:id")
    List<OrderItems> findAllByOrderId(@Param("id") String id);
}
