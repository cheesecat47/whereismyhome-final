package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UpdateMemberRequestDto", description = "회원 수정 파라미터로 받을 Dto")
public class UpdateMemberByIdRequestDto {

    @ApiModelProperty(value = "회원 비밀번호")
    private String password;

    @ApiModelProperty(value = "회원 이름")
    private String name;

    @ApiModelProperty(value = "회원 나이")
    private int age;

    @ApiModelProperty(value = "회원 성별 (M, F)")
    private String sex;

    @ApiModelProperty(value = "회원이 관심 있는 주소")
    private String address;
}
