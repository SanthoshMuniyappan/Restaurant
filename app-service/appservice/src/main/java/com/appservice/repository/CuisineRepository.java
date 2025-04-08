package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Cuisine;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, String> {
}
