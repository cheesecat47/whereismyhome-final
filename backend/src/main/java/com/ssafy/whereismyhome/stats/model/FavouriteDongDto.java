package com.ssafy.whereismyhome.stats.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "FavouriteDongDto", description = "조회수 높은 동 정보를 담을 Dto")
public class FavouriteDongDto {

    @ApiModelProperty(value = "동 코드")
    private String dongCode;

    @ApiModelProperty(value = "시도명")
    private String sidoName;

    @ApiModelProperty(value = "시군구명")
    private String gugunName;

    @ApiModelProperty(value = "동이름")
    private String dongName;

    @ApiModelProperty(value = "조회수")
    private int views;
}
