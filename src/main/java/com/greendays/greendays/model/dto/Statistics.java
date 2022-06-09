package com.greendays.greendays.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Statistics {
    private int numberOfReports;
    private BigDecimal residuals;
    private BigDecimal reciclable;
    private BigDecimal total;
}
