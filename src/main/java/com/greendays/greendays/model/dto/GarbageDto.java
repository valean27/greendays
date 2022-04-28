package com.greendays.greendays.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GarbageDto {
    private Long id;
    private String garbageCode;
    private String garbageName;
}
