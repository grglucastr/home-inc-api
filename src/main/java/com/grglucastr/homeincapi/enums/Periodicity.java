package com.grglucastr.homeincapi.enums;

public enum Periodicity {

    JUST_ONCE("Just Once", "just_once"),
    YEARLY("Yearly", "yearly"),
    MONTHLY("Monthly", "monthly"),
    WEEKLY("Weekly", "weekly"),
    DAILY("Daily", "daily");

    private String value;
    private String patchValue;

    Periodicity(String value, String value2) {
        this.value = value;
        this.patchValue = value2;
    }

    public String getValue() {
        return value;
    }

    public String getPatchValue() {
        return patchValue;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
