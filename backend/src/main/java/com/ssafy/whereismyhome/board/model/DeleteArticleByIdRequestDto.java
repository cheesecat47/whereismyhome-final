package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DeleteArticleByIdRequestDto", description = "게시글 삭제 시 파라미터로 받을 Dto")
public class DeleteArticleByIdRequestDto {

    @ApiModelProperty(value = "글 작성자 ID", required = true)
    private int memberId;

}
