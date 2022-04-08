package com.greendays.greendays.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "client")
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_type", nullable = true)
    private Integer clientType;
}
