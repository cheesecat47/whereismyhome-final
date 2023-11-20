package com.ssafy.whereismyhome.board.model.mapper;

import com.ssafy.whereismyhome.board.model.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface BoardMapper {
    int writeArticle(BoardDto dto) throws SQLException;
}
