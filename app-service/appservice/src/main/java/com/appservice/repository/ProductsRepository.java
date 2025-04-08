package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ors.common.model.Products;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    @Query(nativeQuery = true, value = "select * from products where restaurant_id=:id")
    List<Products> findAllProductsByRestaurant(@Param("id") String id);
}
