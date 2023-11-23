package com.ssafy.whereismyhome.stats.service;

import com.ssafy.whereismyhome.stats.model.AptDealAmountAvgDto;
import com.ssafy.whereismyhome.stats.model.FavouriteAptDto;
import com.ssafy.whereismyhome.stats.model.FavouriteDongDto;

import java.sql.SQLException;
import java.util.List;

public interface StatsService {
    List<FavouriteDongDto> getFavouriteDongTop5() throws SQLException;

    List<FavouriteAptDto> getFavouriteAptTop5() throws SQLException;

    List<AptDealAmountAvgDto> getAptDealAmountAvg(String aptCode) throws SQLException;
}
