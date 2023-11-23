package com.ssafy.whereismyhome.stats.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AptDealAmountAvgDto", description = "아파트 코드가 일치하는 아파트의 최근 5년 평균 거래 가격 정보를 담을 Dto")
public class AptDealAmountAvgDto {

    @ApiModelProperty(value = "아파트 코드")
    private String aptCode;

    @ApiModelProperty(value = "시도명")
    private String sidoName;

    @ApiModelProperty(value = "시군구명")
    private String gugunName;

    @ApiModelProperty(value = "동이름")
    private String dongName;

    @ApiModelProperty(value = "아파트명")
    private String apartmentName;

    @ApiModelProperty(value = "거래년도")
    private int dealYear;

    @ApiModelProperty(value = "평균 거래 가격")
    private double dealAmountAvg;

}
