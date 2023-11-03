package com.ssafy.whereismyhome.house.service;

import com.ssafy.whereismyhome.house.model.mapper.HouseMapper;
import org.springframework.stereotype.Service;

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
}
