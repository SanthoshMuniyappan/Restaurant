package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.RestaurantRegistration;

import java.util.Optional;

@Repository
public interface RestaurantRegisterRepository extends JpaRepository<RestaurantRegistration,String> {

    boolean existsByEmail(String email);

    Optional<RestaurantRegistration> findByEmail(String email);
}
