package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "LogoutResponseDto", description = "로그아웃 결과를 반환할 Dto")
public class LogoutResponseDto {
    private int status;
    private String message;
    private Object data;
}
