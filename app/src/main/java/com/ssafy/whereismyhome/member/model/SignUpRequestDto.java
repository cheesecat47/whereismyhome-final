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
@ApiModel(value = "SignUpRequestDto", description = "회원 가입 파라미터로 받을 Dto")
public class SignUpRequestDto {

    @ApiModelProperty(value = "회원 아이디") // 상세 설명
    @ApiParam(required = true)
    private String user_id;

    @ApiModelProperty(value = "회원 이름")
    @ApiParam(required = true)
    private String name;

    @ApiModelProperty(value = "회원 비밀번호")
    @ApiParam(required = true)
    private String password;

    @ApiModelProperty(value = "회원 나이")
    @ApiParam(required = true)
    private int age;

    @ApiModelProperty(value = "회원 성별 (M, F)")
    @ApiParam(required = true)
    private String sex;

    @ApiModelProperty(value = "회원 이메일")
    @ApiParam(required = true)
    private String email_account;

    @ApiModelProperty(value = "회원 이메일 도메인, @ 뒤에 들어갈 문장 (ex. google.com)")
    @ApiParam(required = true)
    private String email_domain;
}
