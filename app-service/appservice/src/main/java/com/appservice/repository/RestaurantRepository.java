package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ors.common.model.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Restaurant r SET r.ratings = :avgRestaurantRating, r.appRating = :avgAppRating WHERE r.id = :restaurantId")
    void updateAverageRatings(@Param("restaurantId") String restaurantId,@Param("avgRestaurantRating") Double avgRestaurantRating,@Param("avgAppRating") Double avgAppRating);

    @Query("SELECT r.id FROM Restaurant r")
    List<String> findAllIds();


}
