package com.greendays.greendays.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "incident")
@Table(name = "incident")
@Data
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "incident_type", nullable = true)
    private String incidentType;

    @Column(name = "observations", nullable = true)
    private String observations;
}
