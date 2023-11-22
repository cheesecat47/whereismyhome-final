package com.ssafy.whereismyhome.house.service;

import com.ssafy.whereismyhome.house.model.HouseDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDto;

import java.util.List;

public interface HouseService {
    String getDongCodeByDongName(String sidoName, String gugunName, String dongName);

    List<HouseInfoDto> getHouseInfosByDongCode(String dongCode);

    List<HouseInfoDealDto> getHouseInfoDealsByDongCode(String dongCode);

    List<HouseDealDto> getHouseDealsByAptCodeYearMonth(String aptCode, String year, String month);

    List<String> getSidoNames();

    List<String> getGugunNames(String sidoName);

    List<String> getDongNames(String sidoName, String gugunName);

    void addDongViewHistory(String memberId, String dongCode);
}
