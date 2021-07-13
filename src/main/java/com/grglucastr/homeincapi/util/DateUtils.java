package com.grglucastr.homeincapi.util;

import java.time.LocalDate;

public final class DateUtils {

    public static String getMonthlyProgress(int monthNo){

        LocalDate now = LocalDate.now();
        if (now.getMonthValue() > monthNo) {
            return "100%";
        }

        if (now.getMonthValue() < monthNo) {
            return "0%";
        }

        int year = LocalDate.now().getYear();
        LocalDate start = LocalDate.of(year, monthNo, 1);
        final int lengthOfMonth = start.lengthOfMonth();

        double total = (now.getDayOfMonth() * 100.0) / lengthOfMonth;
        return Math.round(total * 100.0) / 100.0 + "%";
    }
}
