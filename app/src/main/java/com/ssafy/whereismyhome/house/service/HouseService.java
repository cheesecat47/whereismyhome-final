package com.ssafy.whereismyhome.house.service;

import com.ssafy.whereismyhome.house.model.HouseInfoDto;

import java.util.List;

public interface HouseService {
    String getDongCodeByDongName(String sidoName, String gugunName, String dongName);

    List<HouseInfoDto> getHouseInfosByDongCode(String dongCode);
}
