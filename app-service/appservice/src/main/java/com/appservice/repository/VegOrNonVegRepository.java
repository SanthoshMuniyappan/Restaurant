package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.VegOrNonVeg;

@Repository
public interface VegOrNonVegRepository extends JpaRepository<VegOrNonVeg, String> {
}
