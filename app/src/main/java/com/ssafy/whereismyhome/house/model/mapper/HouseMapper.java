package com.ssafy.whereismyhome.house.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMapper {
    String getDongCodeByDongName(String sidoName, String gugunName, String dongName);
}
