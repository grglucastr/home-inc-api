package com.grglucastr.homeincapi.repositories;

import java.util.List;

public interface BaseRepository<T> {

    List<T> findAllByUserIdAndActiveTrue(Long userId);
}
