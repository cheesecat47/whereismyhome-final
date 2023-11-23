package com.ssafy.whereismyhome.stats.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetAptDealAmountAvgResponseDto", description = "아파트 코드가 일치하는 특정 아파트의 최근 5년간 평균 거래 가격 목록을 반환할 Dto")
public class GetAptDealAmountAvgResponseDto {
    private int status;
    private String message;
    private Object data;
}
