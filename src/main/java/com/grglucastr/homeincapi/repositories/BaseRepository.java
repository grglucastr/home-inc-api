package com.grglucastr.homeincapi.repositories;

import java.util.List;

public interface BaseRepository<T> {

    default List<T> findAllByUserIdAndActiveTrue(Long userId) {
        return null;
    }
}
