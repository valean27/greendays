package com.greendays.greendays.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "daily_report")
@Table(name = "daily_report")
@Data
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "route_number", nullable = true)
    private Integer routeNumber;

    @Column(name = "uat", nullable = true)
    private String uat;

    @Column(name = "quantity", nullable = true)
    private Double quantity;

    @Column(name = "destination", nullable = true)
    private String destination;

    @Column(name = "driver_name", nullable = true)
    private String driverName;

    @Column(name = "car_number", nullable = true)
    private String carNumber;

    @Column(name = "problems", nullable = true)
    private String problems;

    @OneToOne
    private Incident incident;

    @OneToOne
    private Client client;

    @OneToOne
    private Garbage garbage;

    @Column(name = "route_sheet", nullable = true)
    private String routeSheet;

    @Column(name = "weight_talon", nullable = true)
    private String weightTalon;
}
