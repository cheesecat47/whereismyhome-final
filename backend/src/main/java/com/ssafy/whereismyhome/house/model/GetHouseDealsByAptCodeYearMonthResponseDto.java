package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "GetHouseDealsByAptCodeYearMonthResponseDto", description = "아파트 번호에 해당하는 거래 목록을 반환할 Dto")
public class GetHouseDealsByAptCodeYearMonthResponseDto {
    private int status;
    private String message;
    private List<HouseDealDto> data;
}
