package com.greendays.greendays.model.dto;

import com.greendays.greendays.model.dal.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class DailyReportDto {
    private Long id;
    private Date date;
    private Integer routeNumber;
    private String uat;
    private BigDecimal quantity;
    private String destination;
    private String driverName;
    private String carNumber;
    private String problems;
    private String incidentType;
    private String observations;
    private String clientType;
    private String routeSheet;
    private String weightTalon;
    private String garbageCode;
    private String garbageName;
}
