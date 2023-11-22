package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "WriteArticleResponseDto", description = "글 생성 결과를 반환할 Dto")
public class WriteArticleResponseDto {

    private int status;

    private String message;

    @ApiModelProperty(notes = "글 생성 성공 시 null 반환, 실패 시 파라미터 반환.")
    private Object data;
}
