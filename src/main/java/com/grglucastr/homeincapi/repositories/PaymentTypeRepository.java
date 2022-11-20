package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long>,
        BaseRepository<PaymentType> {

    @Override
    List<PaymentType> findAllByActiveTrue();
}
