package com.grglucastr.homeincapi.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTests {

    @Test
    public void testMonthProgressGivenPreviousYear(){
        final int year = LocalDate.now().getYear() - 1;
        final int month = 4;

        final String monthlyProgress = DateUtils.getMonthlyProgress(year, month);
        Assert.assertThat(monthlyProgress, Matchers.is("100%"));
    }

    @Test
    public void testMonthProgressGivenCurrentYearAndPreviousMonth(){
        final int year = LocalDate.now().getYear();
        final int month = LocalDate.now().minusMonths(1L).getMonthValue();

        final String monthlyProgress = DateUtils.getMonthlyProgress(year, month);
        Assert.assertThat(monthlyProgress, Matchers.is("100%"));
    }

    @Test
    public void testMonthProgressGivenNextYear() {

        final int year = LocalDate.now().getYear() + 1;
        final int month = 1;
        final String monthlyProgress = DateUtils.getMonthlyProgress(year, month);
        Assert.assertThat(monthlyProgress, Matchers.is("0%"));
    }

    @Test
    public void testMonthProgressGivenCurrentYearButNextMonth() {

        final int year = LocalDate.now().getYear();
        final int month = LocalDate.now().plusMonths(1L).getMonthValue();

        final String monthlyProgress = DateUtils.getMonthlyProgress(year, month);
        Assert.assertThat(monthlyProgress, Matchers.is("0%"));
    }

    @Test
    public void testMonthProgressGivenCurrentYearAndCurrentMonth() {

        final int year = LocalDate.now().getYear();
        final int month = LocalDate.now().getMonthValue();

        final String monthlyProgress = DateUtils.getMonthlyProgress(year, month);
        Assert.assertThat(monthlyProgress, Matchers.is(Matchers.not("0%")));
        Assert.assertThat(monthlyProgress, Matchers.is(Matchers.not("100%")));
    }

}
