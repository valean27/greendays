package com.greendays.greendays.model;

import javax.persistence.*;

@Entity(name = "garbage")
@Table(name = "garbage")
public class Garbage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "garbage_code", nullable = true)
    private Integer garbageCode;

    @Column(name = "garbage_name", nullable = true)
    private String garbageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGarbageCode() {
        return garbageCode;
    }

    public void setGarbageCode(Integer garbageCode) {
        this.garbageCode = garbageCode;
    }

    public String getGarbageName() {
        return garbageName;
    }

    public void setGarbageName(String garbageName) {
        this.garbageName = garbageName;
    }
}
