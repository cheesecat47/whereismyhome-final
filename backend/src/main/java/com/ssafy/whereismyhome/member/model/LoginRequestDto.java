package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "LoginRequestDto", description = "로그인 파라미터로 받을 Dto")
public class LoginRequestDto {

    @ApiModelProperty(value = "회원 이메일. 도메인은 gmail.com 또는 ssafy.com만 사용 가능.", required = true)
    private String email;

    @ApiModelProperty(value = "회원 비밀번호", required = true)
    private String password;
}
