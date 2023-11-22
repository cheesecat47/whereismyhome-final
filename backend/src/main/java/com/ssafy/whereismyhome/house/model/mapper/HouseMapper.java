package com.ssafy.whereismyhome.house.model.mapper;

import com.ssafy.whereismyhome.house.model.HouseDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseMapper {
    String getDongCodeByDongName(String sidoName, String gugunName, String dongName);

    List<HouseInfoDto> getHouseInfosByDongCode(String dongCode);

    List<HouseInfoDealDto> getHouseInfoDealsByDongCode(String dongCode);

    List<HouseDealDto> getHouseDealsByAptCodeYearMonth(String aptCode, String year, String month);

    List<String> getSidoNames();

    List<String> getGugunNames(String sidoName);

    List<String> getDongNames(String sidoName, String gugunName);

    void addDongViewHistory(String memberId, String dongCode);

    void addAptViewHistory(String memberId, String aptCode);
}
