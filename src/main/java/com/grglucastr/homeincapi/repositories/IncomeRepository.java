package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long>,
        BaseRepository<Income> {

    @Override
    List<Income> findAllByUserIdAndActiveTrue(Long userId);
}
