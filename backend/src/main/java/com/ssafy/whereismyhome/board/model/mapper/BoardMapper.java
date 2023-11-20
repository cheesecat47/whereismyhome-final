package com.ssafy.whereismyhome.board.model.mapper;

import com.ssafy.whereismyhome.board.model.BoardDetailDto;
import com.ssafy.whereismyhome.board.model.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BoardMapper {
    int writeArticle(BoardDto dto) throws SQLException;

    List<BoardDto> getNotices() throws SQLException;

    List<BoardDto> getCommunityArticles(String dongCode) throws SQLException;

    BoardDetailDto getArticleById(int boardId) throws SQLException;

    int updateArticleById(BoardDetailDto dto) throws SQLException;
}
