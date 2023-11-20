package com.ssafy.whereismyhome.board.service;

import com.ssafy.whereismyhome.board.model.BoardDto;

import java.sql.SQLException;

public interface BoardService {
    int writeArticle(BoardDto dto) throws SQLException;
}
