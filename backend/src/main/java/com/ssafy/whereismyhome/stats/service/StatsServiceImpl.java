package com.ssafy.whereismyhome.stats.service;

import com.ssafy.whereismyhome.stats.model.FavouriteDongDto;
import com.ssafy.whereismyhome.stats.model.mapper.StatsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsMapper statsMapper;

    @Override
    public List<FavouriteDongDto> getFavouriteDongTop5() throws SQLException {
        return statsMapper.getFavouriteDongTop5();
    }
}
