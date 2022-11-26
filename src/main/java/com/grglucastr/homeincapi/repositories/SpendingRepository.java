package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long>,
        BaseRepository<Spending> {

    @Override
    List<Spending> findAllByUserIdAndActiveTrue(Long userId);
}
