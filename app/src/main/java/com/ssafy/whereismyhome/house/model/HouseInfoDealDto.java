package com.ssafy.whereismyhome.house.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class HouseInfoDealDto {
    private String dong;
    private String dongCode;
    private String jibun;
    private String apartmentName;
    private long aptCode;
    private String lng;
    private String lat;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String dealAmount;
    private String area;
}
