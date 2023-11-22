package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetArticleByIdResponseDto", description = "게시글 아이디에 해당하는 글 정보를 반환할 Dto")
public class GetArticleByIdResponseDto {

    private int status;

    private String message;

    private Object data;
}
