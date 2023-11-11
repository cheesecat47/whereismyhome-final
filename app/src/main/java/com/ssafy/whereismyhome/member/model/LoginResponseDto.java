package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "LoginResponseDto", description = "로그인 결과를 반환할 Dto")
public class LoginResponseDto {
    private int status;
    private String message;
    private MemberDto data;
}
