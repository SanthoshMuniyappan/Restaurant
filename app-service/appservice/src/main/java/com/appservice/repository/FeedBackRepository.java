package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ors.common.model.FeedBack;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,String> {

    @Query("SELECT AVG(f.restaurantRating) FROM FeedBack f WHERE f.restaurant.id = :restaurantId")
    Double findAverageRatingByRestaurantId(@Param("restaurantId") String restaurantId);

    @Query("SELECT AVG(f.appRating) FROM FeedBack f WHERE f.restaurant.id = :restaurantId")
    Double findAverageAppRatingByRestaurantId(String restaurantId);

}
