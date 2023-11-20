package com.ssafy.whereismyhome.board.service;

import com.ssafy.whereismyhome.board.model.BoardDetailDto;
import com.ssafy.whereismyhome.board.model.BoardDto;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
    int writeArticle(BoardDto dto) throws SQLException;

    List<BoardDto> getNotices() throws SQLException;

    List<BoardDto> getCommunityArticles(String dongCode) throws SQLException;

    BoardDetailDto getArticleById(int boardId) throws SQLException;

    int updateArticleById(BoardDetailDto dto) throws SQLException;

    int deleteArticleById(int boardId) throws SQLException;
}
