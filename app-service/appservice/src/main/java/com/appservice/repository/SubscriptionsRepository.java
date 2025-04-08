package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ors.common.model.Subscriptions;

import java.util.List;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, String> {

    @Query("select s from Subscriptions s where s.restaurant.id=:id")
    List<Subscriptions> findAllRestaurantById(@Param(("id")) String id);

    @Modifying
    @Query("DELETE FROM Subscriptions s WHERE s.id = :id")
    void deleteById(@Param("id") final String id);

}
