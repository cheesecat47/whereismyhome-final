package com.ssafy.whereismyhome.stats.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetFavouriteDongTop5ResponseDto", description = "최근 7일간 인기 있는 (조회수가 많은) 동 top 5 이름 join해서 출력할 Dto")
public class GetFavouriteDongTop5ResponseDto {
    private int status;
    private String message;
    private Object data;
}
