package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetLocationNamesResponseDto", description = "시도, 시구군, 동 이름 목록을 반환할 Dto")
public class GetLocationNamesResponseDto {
    private int status;
    private String message;
    private Object data;
}
