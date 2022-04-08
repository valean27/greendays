package com.greendays.greendays.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "garbage")
@Table(name = "garbage")
@Data
public class Garbage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "garbage_code", nullable = true)
    private String garbageCode;

    @Column(name = "garbage_name", nullable = true)
    private String garbageName;

}