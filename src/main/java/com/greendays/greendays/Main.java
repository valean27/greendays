package com.greendays.greendays;

import java.math.BigDecimal;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(1234);
        System.out.println(bigDecimal.divide(new BigDecimal(1000, new MathContext(10))));
    }
}
