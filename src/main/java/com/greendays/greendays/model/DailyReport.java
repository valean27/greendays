package com.greendays.greendays.model;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "daily_report")
@Table(name = "daily_report")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getUat() {
        return uat;
    }

    public void setUat(String uat) {
        this.uat = uat;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Garbage getGarbage() {
        return garbage;
    }

    public void setGarbage(Garbage garbage) {
        this.garbage = garbage;
    }

    public String getRouteSheet() {
        return routeSheet;
    }

    public void setRouteSheet(String routeSheet) {
        this.routeSheet = routeSheet;
    }

    public String getWeightTalon() {
        return weightTalon;
    }

    public void setWeightTalon(String weightTalon) {
        this.weightTalon = weightTalon;
    }
}
