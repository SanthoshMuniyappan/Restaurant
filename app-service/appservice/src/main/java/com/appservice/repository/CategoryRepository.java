package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
