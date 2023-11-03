package com.ssafy.whereismyhome.house.controller;

import com.ssafy.whereismyhome.house.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/house")
@Api(value = "House")
@CrossOrigin("*")
public class HouseController {

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
    private final HouseService service;

    public HouseController(HouseService service) {
        this.service = service;
    }

    @GetMapping("/search/dongCode")
    @ApiOperation(value = "동 이름으로 동 코드 검색")
    public ResponseEntity<String> getDongCodeByDongName(String sidoName, String gugunName, String dongName) {
        String dongCode = service.getDongCodeByDongName(sidoName, gugunName, dongName);
        logger.debug("dongCode: " + dongCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dongCode);
    }
}
