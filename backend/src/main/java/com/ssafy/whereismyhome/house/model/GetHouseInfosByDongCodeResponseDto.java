package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetHouseInfosByDongCodeResponseDto", description = "동 코드에 해당하는 아파트 목록을 반환할 Dto")
public class GetHouseInfosByDongCodeResponseDto {
    private int status;
    private String message;
    private Object data;
}
