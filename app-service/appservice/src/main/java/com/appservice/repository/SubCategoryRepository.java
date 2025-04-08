package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,String > {
}
