package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

}
