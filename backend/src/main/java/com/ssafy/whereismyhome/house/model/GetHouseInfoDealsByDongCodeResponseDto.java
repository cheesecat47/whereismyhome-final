package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetHouseInfoDealsByDongCodeResponseDto", description = "동 코드로 아파트 최근 거래 목록 목록을 반환할 Dto")
public class GetHouseInfoDealsByDongCodeResponseDto {
    private int status;
    private String message;
    private Object data;
}
