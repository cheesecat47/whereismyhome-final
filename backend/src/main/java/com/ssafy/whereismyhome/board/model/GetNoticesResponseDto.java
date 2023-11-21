package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "GetNoticesResponseDto", description = "공지글 목록을 반환할 Dto")
public class GetNoticesResponseDto {

    private int status;

    private String message;

    private List<BoardListDto> data;
}
