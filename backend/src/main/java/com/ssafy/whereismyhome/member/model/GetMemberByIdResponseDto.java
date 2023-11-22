package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetMemberResponseDto", description = "특정 회원 정보를 반환할 Dto")
public class GetMemberByIdResponseDto {
    private int status;
    private String message;
    private Object data;
}
