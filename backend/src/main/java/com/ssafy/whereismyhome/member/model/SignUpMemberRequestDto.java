package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SignUpMemberRequestDto", description = "회원 등록 파라미터로 받을 Dto")
public class SignUpMemberRequestDto {

    @ApiModelProperty(value = "회원 아이디", required = true) // 상세 설명
    private String memberId;

    @ApiModelProperty(value = "회원 이름", required = true)
    private String name;

    @ApiModelProperty(value = "회원 비밀번호", required = true)
    private String password;

    @ApiModelProperty(value = "회원 나이", required = true)
    private int age;

    @ApiModelProperty(value = "회원 성별 (M, F)", required = true)
    private String sex;

    @ApiModelProperty(value = "회원 이메일", required = true)
    private String emailAccount;

    @ApiModelProperty(value = "회원 이메일 도메인, @ 뒤에 들어갈 문장 (ex. google.com)", required = true)
    private String emailDomain;

    @ApiModelProperty(value = "회원이 살고 있는 지역", required = true)
    private String dongCode;
}
