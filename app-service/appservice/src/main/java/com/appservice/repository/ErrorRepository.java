package com.appservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ors.common.model.Error;

@Repository
public interface ErrorRepository extends JpaRepository<Error,String> {
}
