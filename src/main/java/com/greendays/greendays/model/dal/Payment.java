package com.greendays.greendays.model.dal;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity(name = "payment")
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uat", nullable = true)
    private String uat;

    @Column(name = "total_dc", nullable = true)
    private BigDecimal totalDc;

    @Column(name = "physical_person", nullable = true)
    private BigDecimal physicalPerson;

    @Column(name = "juridical_person", nullable = true)
    private BigDecimal juridicalPerson;

    @Column(name = "ocasional_services", nullable = true)
    private BigDecimal ocasionalServices;

    @Column(name = "redevence", nullable = true)
    private BigDecimal redevence;

    @Column(name = "document", nullable = true)
    private String document;

    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "trimester", nullable = true)
    private int trimester;

    @Column(name = "year", nullable = true)
    private int year;


}
