package com.ssafy.whereismyhome.house.service;

import com.ssafy.whereismyhome.house.model.HouseDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDto;
import com.ssafy.whereismyhome.house.model.mapper.HouseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseMapper mapper;

    public HouseServiceImpl(HouseMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String getDongCodeByDongName(String sidoName, String gugunName, String dongName) {
        return mapper.getDongCodeByDongName(sidoName, gugunName, dongName);
    }

    @Override
    public List<HouseInfoDto> getHouseInfosByDongCode(String dongCode) {
        return mapper.getHouseInfosByDongCode(dongCode);
    }

    @Override
    public List<HouseInfoDealDto> getHouseInfoDealsByDongCode(String dongCode) {
        return mapper.getHouseInfoDealsByDongCode(dongCode);
    }

    @Override
    public List<HouseDealDto> getHouseDealsByAptCodeYearMonth(String aptCode, String year, String month) {
        return mapper.getHouseDealsByAptCodeYearMonth(aptCode, year, month);
    }
}
