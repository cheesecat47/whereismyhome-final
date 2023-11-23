package com.ssafy.whereismyhome.house.service;

import com.ssafy.whereismyhome.house.model.HouseDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDto;
import com.ssafy.whereismyhome.house.model.mapper.HouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseMapper houseMapper;

    @Override
    public String getDongCodeByDongName(String sidoName, String gugunName, String dongName) {
        return houseMapper.getDongCodeByDongName(sidoName, gugunName, dongName);
    }

    @Override
    public List<HouseInfoDto> getHouseInfosByDongCode(String dongCode) {
        return houseMapper.getHouseInfosByDongCode(dongCode);
    }

    @Override
    public List<HouseInfoDealDto> getHouseInfoDealsByDongCode(String dongCode) {
        return houseMapper.getHouseInfoDealsByDongCode(dongCode);
    }

    @Override
    public List<HouseDealDto> getHouseDealsByAptCodeYearMonth(String aptCode, String year, String month) {
        return houseMapper.getHouseDealsByAptCodeYearMonth(aptCode, year, month);
    }

    @Override
    public List<String> getSidoNames() {
        return houseMapper.getSidoNames();
    }

    @Override
    public List<String> getGugunNames(String sidoName) {
        return houseMapper.getGugunNames(sidoName);
    }

    @Override
    public List<String> getDongNames(String sidoName, String gugunName) {
        return houseMapper.getDongNames(sidoName, gugunName);
    }

    @Override
    public void addDongViewHistory(String memberId, String dongCode) {
        houseMapper.addDongViewHistory(memberId, dongCode);
    }

    @Override
    public void addAptViewHistory(String memberId, String aptCode) {
        houseMapper.addAptViewHistory(memberId, aptCode);
    }
}
