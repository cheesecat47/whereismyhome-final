package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "LoginRequestDto", description = "로그인 파라미터로 받을 Dto")
public class LoginRequestDto {

    @ApiModelProperty(value = "회원 아이디", required = true) // 상세 설명
    private String memberId;

    @ApiModelProperty(value = "회원 비밀번호", required = true)
    private String password;
}
