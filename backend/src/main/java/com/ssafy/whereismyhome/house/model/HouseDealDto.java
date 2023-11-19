package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "HouseDealDto", description = "거래 정보를 관리할 Dto")
public class HouseDealDto {
    private long dealId;
    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String cancelDealType;
    private long aptCode;
}
