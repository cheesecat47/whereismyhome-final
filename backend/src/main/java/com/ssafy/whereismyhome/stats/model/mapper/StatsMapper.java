package com.ssafy.whereismyhome.stats.model.mapper;

import com.ssafy.whereismyhome.stats.model.AptDealAmountAvgDto;
import com.ssafy.whereismyhome.stats.model.FavouriteAptDto;
import com.ssafy.whereismyhome.stats.model.FavouriteDongDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface StatsMapper {
    List<FavouriteDongDto> getFavouriteDongTop5() throws SQLException;

    List<FavouriteAptDto> getFavouriteAptTop5() throws SQLException;

    List<AptDealAmountAvgDto> getAptDealAmountAvg(String aptCode) throws SQLException;
}
