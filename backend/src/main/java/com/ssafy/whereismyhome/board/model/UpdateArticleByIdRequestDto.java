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

    @ApiModelProperty(value = "글 작성자 ID", required = true,
            notes = "작성자는 변경 불가. 이 값은 글 작성자가 현재 로그인한 사용자가 맞는지 확인하기 위함. JWT 사용하도록 바꾼 후에는 변경될 수 있음.")
    private int memberId;

}
