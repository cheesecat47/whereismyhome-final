package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "SignUpMemberResponseDto", description = "회원 등록 결과를 반환할 Dto")
public class SignUpMemberResponseDto {
    private int status;
    private String message;
    private Object data;
}
