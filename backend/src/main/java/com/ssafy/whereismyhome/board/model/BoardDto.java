package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BoardDto", description = "게시판 글 정보를 관리할 Dto")
public class BoardDto {

    @ApiModelProperty(value = "글 ID")
    private int boardId;

    @ApiModelProperty(value = "글 제목")
    private String title;

    @ApiModelProperty(value = "글 내용")
    private String content;

    @ApiModelProperty(value = "글 타입", notes = "공지는 1, 일반 글은 0. 관리자만 설정 가능.")
    private int type;

    @ApiModelProperty(value = "글 작성자 ID")
    private int memberId;

    @ApiModelProperty(value = "글 작성자 이메일")
    private String memberEmail;

    @ApiModelProperty(value = "글 작성자 이름")
    private String memberName;

    @ApiModelProperty(value = "동 코드", notes = "지역별 게시판에 사용")
    private String dongCode;

    @ApiModelProperty(value = "조회수")
    private int hit;

    @ApiModelProperty(value = "글 생성 시간")
    private String createdTime;

    @ApiModelProperty(value = "글 수정 시간")
    private String updatedTime;

}
