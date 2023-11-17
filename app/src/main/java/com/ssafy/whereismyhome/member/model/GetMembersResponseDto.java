package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "GetMembersResponseDto", description = "전체 회원 조회 결과를 반환할 Dto")
public class GetMembersResponseDto {
    private int status;
    private String message;
    private List<MemberDto> data;
}
