package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
}
