package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.MealTime;

@Repository
public interface MealTimeRepository extends JpaRepository<MealTime, String> {
}
