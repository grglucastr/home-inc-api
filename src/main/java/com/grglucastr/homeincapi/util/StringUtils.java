package com.grglucastr.homeincapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class StringUtils {

    public static String asJsonString(final Object obj){

        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
