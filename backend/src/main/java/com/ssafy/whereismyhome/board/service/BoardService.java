package com.ssafy.whereismyhome.board.service;

import com.ssafy.whereismyhome.board.model.BoardDetailDto;
import com.ssafy.whereismyhome.board.model.BoardListDto;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
    int writeArticle(BoardDetailDto dto) throws SQLException;

    List<BoardListDto> getNotices(String orderBy) throws SQLException;

    List<BoardListDto> getCommunityArticles(String dongCode, String orderBy) throws SQLException;

    BoardDetailDto getArticleById(int boardId) throws SQLException;

    int updateArticleById(BoardDetailDto dto) throws SQLException;

    int deleteArticleById(int boardId) throws SQLException;
}
