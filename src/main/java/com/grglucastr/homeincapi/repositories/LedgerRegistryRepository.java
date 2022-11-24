package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.LedgerRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRegistryRepository extends JpaRepository<LedgerRegistry, Long>,
    BaseRepository<LedgerRegistry>{

    @Override
    List<LedgerRegistry> findAllByActiveTrue();
}
