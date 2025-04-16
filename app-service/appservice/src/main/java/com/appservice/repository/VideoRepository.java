package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
}
