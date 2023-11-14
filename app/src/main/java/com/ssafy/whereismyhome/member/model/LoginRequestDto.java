package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "LoginRequestDto", description = "로그인 파라미터로 받을 Dto")
public class LoginRequestDto {

    @ApiModelProperty(value = "회원 아이디") // 상세 설명
    @ApiParam(required = true)
    private String user_id;

    @ApiModelProperty(value = "회원 비밀번호")
    @ApiParam(required = true)
    private String password;
}
