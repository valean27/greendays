package com.greendays.greendays.model.dto;

public enum Trimester {
    I(1, 2, 3),
    II(4, 5, 6),
    III(7, 8, 9),
    IV(10, 11, 12);

    private final int month1;
    private final int month2;
    private final int month3;

    Trimester(int month1, int month2, int month3) {
        this.month1 = month1;
        this.month2 = month2;
        this.month3 = month3;
    }

    public static Trimester valueOf(int quarterNumber){
        switch (quarterNumber){
            case 1: return I;
            case 2: return II;
            case 3: return III;
            case 4: return IV;
        }
        return null;
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
