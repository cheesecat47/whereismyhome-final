package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "MemberDto (회원정보)", description = "회원정보를 관리 할 Dto")
public class MemberDto {

    @ApiModelProperty(value = "회원 아이디") // 상세 설명
    private int memberId;

    @ApiModelProperty(value = "회원 이메일")
    private String emailAccount;

    @ApiModelProperty(value = "회원 이메일 도메인, @ 뒤에 들어갈 문장 (ex. google.com)")
    private String emailDomain;

    @ApiModelProperty(value = "회원 비밀번호")
    private String password;

    @ApiModelProperty(value = "회원 이름")
    private String name;

    @ApiModelProperty(value = "회원 나이")
    private int age;

    @ApiModelProperty(value = "회원 성별 (M, F)")
    private String sex;

    @ApiModelProperty(value = "회원 권한")
    private String authorization;

    @ApiModelProperty(value = "refresh token")
    private String refreshToken;

    @ApiModelProperty(value = "회원이 관심 있는 주소")
    private String address;

    @ApiModelProperty(value = "회원이 관심 있는 지역 코드")
    private String dongCode;

    @ApiModelProperty(value = "회원 생성 시간")
    private String createdTime;

    @ApiModelProperty(value = "회원 수정 시간")
    private String updatedTime;
}
