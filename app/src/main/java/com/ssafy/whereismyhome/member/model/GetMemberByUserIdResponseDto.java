package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "GetMemberResponseDto", description = "특정 회원 정보를 반환할 Dto")
public class GetMemberByUserIdResponseDto {
    private int status;
    private String message;
    private MemberDto data;
}
