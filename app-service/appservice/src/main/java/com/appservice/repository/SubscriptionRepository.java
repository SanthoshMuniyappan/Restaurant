package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ors.common.model.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    @Query("select s from Subscription s where s.restaurant.id=:id")
    List<Subscription> findAllRestaurantById(@Param(("id")) String id);

    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.id = :id")
    void deleteById(@Param("id") final String id);

}
