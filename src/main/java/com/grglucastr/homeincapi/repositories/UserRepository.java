package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
