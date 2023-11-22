package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "UpdateMemberResponseDto", description = "회원 수정 결과를 반환할 Dto")
public class UpdateMemberByIdResponseDto {
    private int status;
    private String message;
    private Object data;
}
