package com.grglucastr.homeincapi.mocks;

import java.time.LocalDateTime;

public class MockLocalDate {

    public static LocalDateTime getInsertDateTime(){
        return LocalDateTime.of(2035, 9, 10, 14, 21, 34);
    }

    public static LocalDateTime getUpdateDateTime(){
        return LocalDateTime.of(2035, 9, 15, 21, 42, 34);
    }
}
