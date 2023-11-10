package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "GetMembersResponseDto", description = "전체 회원 조회 결과를 반환할 Dto")
public class GetMembersResponseDto {
    private HttpStatus status;
    private String message;
    private List<MemberDto> data;
}
