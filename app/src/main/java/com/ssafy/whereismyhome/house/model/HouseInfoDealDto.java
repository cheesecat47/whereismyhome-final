package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "HouseInfoDealDto", description = "아파트의 거래 정보를 관리할 Dto")
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
