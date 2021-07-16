package com.grglucastr.homeincapi.util;

import java.time.LocalDate;

public final class DateUtils {

    public static String getMonthlyProgress(int year, int month){

        final LocalDate now = LocalDate.now();
        if(now.getYear() > year)
            return "100%";

        if(year > now.getYear())
            return "0%";

        if(now.getMonthValue() > month)
            return "100%";

        if(month > now.getMonthValue())
            return "0%";

        final LocalDate paramDate = LocalDate.of(year, month, 1);
        double total = (now.getDayOfMonth() * 100.0) / paramDate.lengthOfMonth();
        return Math.round(total * 100.0) / 100.0 + "%";
    }
}
