package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.SpendingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpendingCategoryRepository extends JpaRepository<SpendingCategory, Long>,
        BaseRepository<SpendingCategory> {

    @Override
    List<SpendingCategory> findAllByUserIdAndActiveTrue(Long userId);
}
