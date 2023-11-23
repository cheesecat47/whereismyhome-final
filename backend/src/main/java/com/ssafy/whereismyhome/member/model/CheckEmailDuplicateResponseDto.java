package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "checkEmailDuplicateResponseDto", description = "이메일 중복 체크 결과를 반환할 Dto")
public class CheckEmailDuplicateResponseDto {
    private int status;
    private String message;
    private Object data;
}
