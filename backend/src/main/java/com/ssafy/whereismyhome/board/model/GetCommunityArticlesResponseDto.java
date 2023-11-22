package com.ssafy.whereismyhome.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "GetCommunityArticlesResponseDto", description = "동네 글 목록을 반환할 Dto")
public class GetCommunityArticlesResponseDto {

    private int status;

    private String message;

    private Object data;
}
