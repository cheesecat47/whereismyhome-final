package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "RefreshTokenResponseDto", description = "Access Token 재발급 결과를 반환할 Dto")
public class RefreshTokenResponseDto {
    private int status;
    private String message;
    private Object data;
}
