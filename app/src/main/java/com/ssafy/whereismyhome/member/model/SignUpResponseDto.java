package com.ssafy.whereismyhome.member.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "SignUpResponseDto", description = "회원가입 결과를 반환할 Dto")
public class SignUpResponseDto {
    private HttpStatus status;
    private String message;
}
