package com.ssafy.whereismyhome.house.controller;

import com.ssafy.whereismyhome.house.model.HouseDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDealDto;
import com.ssafy.whereismyhome.house.model.HouseInfoDto;
import com.ssafy.whereismyhome.house.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/house")
@Api(value = "House", tags = {"아파트 정보 API V1"})
@CrossOrigin("*")
@Slf4j
public class HouseController {

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
    private final HouseService service;

    public HouseController(HouseService service) {
        this.service = service;
    }

    @GetMapping("/dongCode")
    @ApiOperation(value = "동 이름으로 동 코드 검색")
    public ResponseEntity<String> getDongCodeByDongName(String sidoName, String gugunName, String dongName) {
        String dongCode = service.getDongCodeByDongName(sidoName, gugunName, dongName);
        logger.debug("dongCode: {}", dongCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dongCode);
    }

    @GetMapping("/houseinfo")
    @ApiOperation(value = "동 코드로 아파트 목록 조회")
    public ResponseEntity<List<HouseInfoDto>> getHouseInfosByDongCode(String dongCode) {
        List<HouseInfoDto> list = service.getHouseInfosByDongCode(dongCode);
        logger.debug("houseInfoList: {}", list);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }

    @GetMapping("/houseinfodeal")
    @ApiOperation(value = "동 코드로 아파트 최근 거래 목록 조회")
    public ResponseEntity<List<HouseInfoDealDto>> getHouseInfoDealsByDongCode(String dongCode) {
        List<HouseInfoDealDto> list = service.getHouseInfoDealsByDongCode(dongCode);
        logger.debug("houseInfoDealList: {}", list);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }

    @GetMapping("/housedeal")
    @ApiOperation(value = "아파트 번호로 거래 목록 조회")
    public ResponseEntity<List<HouseDealDto>> getHouseDealsByAptCodeYearMonth(String aptCode, String year, String month) {
        List<HouseDealDto> list = service.getHouseDealsByAptCodeYearMonth(aptCode, year, month);
        logger.debug("houseDealList: {}", list);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }
}
