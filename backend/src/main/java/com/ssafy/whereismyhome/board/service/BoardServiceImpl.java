package com.ssafy.whereismyhome.board.service;

import com.ssafy.whereismyhome.board.model.BoardDto;
import com.ssafy.whereismyhome.board.model.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public int writeArticle(BoardDto dto) throws SQLException {
        return boardMapper.writeArticle(dto);
    }

    @Override
    public List<BoardDto> getNotices() throws SQLException {
        return boardMapper.getNotices();
    }
}
