package com.greendays.greendays.service;

import lombok.Data;

public enum Quarter {
    FIRST_QUARTER(1,2,3),
    SECOND_QUARTER(4,5,6),
    THIRD_QUARTER(7,8,9),
    FORTH_QUARTER(10,11,12);

    int month1;
    int month2;
    int month3;

    Quarter(int month1, int month2, int month3) {
        this.month1 = month1;
        this.month2 = month2;
        this.month3 = month3;
    }

    public int getMonth1() {
        return month1;
    }

    public int getMonth2() {
        return month2;
    }

    public int getMonth3() {
        return month3;
    }
}
