package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "SignUpMemberResponseDto", description = "회원 등록 결과를 반환할 Dto")
public class SignUpMemberResponseDto {
    private HttpStatus status;
    private String message;
}
