package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long>,
        BaseRepository<IncomeCategory> {

    @Override
    List<IncomeCategory> findAllByActiveTrue();
}
