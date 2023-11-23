package com.ssafy.whereismyhome.stats.service;

import com.ssafy.whereismyhome.stats.model.FavouriteDongDto;

import java.sql.SQLException;
import java.util.List;

public interface StatsService {
    List<FavouriteDongDto> getFavouriteDongTop5() throws SQLException;
}
