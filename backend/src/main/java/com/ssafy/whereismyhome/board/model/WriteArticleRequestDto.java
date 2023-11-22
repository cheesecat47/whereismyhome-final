package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "WriteArticleRequestDto", description = "게시판 작성 시 파라미터로 받을 Dto")
public class WriteArticleRequestDto {

    @ApiModelProperty(value = "글 제목", required = true)
    private String title;

    @ApiModelProperty(value = "글 내용", required = true)
    private String content;

    @ApiModelProperty(value = "글 작성자 ID", required = true)
    private int memberId;

    @ApiModelProperty(value = "동 코드", notes = "지역별 게시판에 사용. 빈 칸이면 작성자의 지역으로 자동 설정.")
    private String dongCode;
}
