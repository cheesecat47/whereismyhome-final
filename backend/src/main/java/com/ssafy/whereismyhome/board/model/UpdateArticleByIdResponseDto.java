package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "UpdateArticleByIdResponseDto", description = "글 수정 결과를 반환할 Dto")
public class UpdateArticleByIdResponseDto {

    private int status;

    private String message;

    private Object data;

}
