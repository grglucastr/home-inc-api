package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.Cors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorsRepository extends JpaRepository<Cors, Long> {
}
