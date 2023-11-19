package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetDongCodeByDongNameResponseDto", description = "동 이름에 해당하는 동 코드를 반환할 Dto")
public class GetDongCodeByDongNameResponseDto {
    private int status;
    private String message;
    private String data;
}
