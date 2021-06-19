package com.grglucastr.homeincapi.enums;

public enum Periodicity {

    JUST_ONCE("Just Once"),
    YEARLY("Yearly"),
    MONTHLY("Monthly"),
    WEEKLY("Weekly"),
    DAILY("Daily");

    private String value;

    Periodicity(String str){
        value = str;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
