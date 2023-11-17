package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "DeleteMemberByUserIdResponseDto", description = "회원 삭제 결과를 반환할 Dto")
public class DeleteMemberByIdResponseDto {
    private int status;
    private String message;
}
