package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.FundDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundDetailRepository extends JpaRepository<FundDetail, Long>,
        BaseRepository<FundDetail>{

    @Override
    List<FundDetail> findAllByActiveTrue();
}
