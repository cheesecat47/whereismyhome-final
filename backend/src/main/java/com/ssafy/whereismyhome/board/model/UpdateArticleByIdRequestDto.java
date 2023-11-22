package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UpdateArticleByIdRequestDto", description = "게시글 수정 시 파라미터로 받을 Dto")
public class UpdateArticleByIdRequestDto {

    @ApiModelProperty(value = "글 제목", required = true)
    private String title;

    @ApiModelProperty(value = "글 내용", required = true)
    private String content;

}
