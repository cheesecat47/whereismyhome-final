package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "DeleteArticleByIdResponseDto", description = "글 삭제 결과를 반환할 Dto")
public class DeleteArticleByIdResponseDto {

    private int status;

    private String message;

}
