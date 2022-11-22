package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.FixedIncomeFund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedIncomeFundRepository extends JpaRepository<FixedIncomeFund, Long>,
        BaseRepository<FixedIncomeFund>{

    @Override
    List<FixedIncomeFund> findAllByActiveTrue();
}
