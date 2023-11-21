package com.ssafy.whereismyhome.board.model.mapper;

import com.ssafy.whereismyhome.board.model.BoardDetailDto;
import com.ssafy.whereismyhome.board.model.BoardListDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BoardMapper {
    int writeArticle(BoardDetailDto dto) throws SQLException;

    List<BoardListDto> getNotices() throws SQLException;

    List<BoardListDto> getCommunityArticles(String dongCode) throws SQLException;

    BoardDetailDto getArticleById(int boardId) throws SQLException;

    int updateArticleById(BoardDetailDto dto) throws SQLException;

    int deleteArticleById(int boardId) throws SQLException;
}
