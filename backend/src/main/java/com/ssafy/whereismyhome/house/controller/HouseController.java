package com.ssafy.whereismyhome.house.controller;

import com.ssafy.whereismyhome.house.model.*;
import com.ssafy.whereismyhome.house.service.HouseService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "House", tags = {"아파트 정보 API V1"})
@RequestMapping("/house")
@Slf4j
public class HouseController {

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @ApiOperation(value = "getDongCodeByDongName", notes = "동 이름으로 동 코드 조회")
    @GetMapping("/dongCode")
    @ApiResponses({
            @ApiResponse(code = 200, message = "동 코드 조회 성공", response = GetDongCodeByDongNameResponseDto.class),
            @ApiResponse(code = 400, message = "동 코드 조회 실패", response = GetDongCodeByDongNameResponseDto.class),
            @ApiResponse(code = 500, message = "동 코드 조회 실패", response = GetDongCodeByDongNameResponseDto.class)
    })
    public ResponseEntity<GetDongCodeByDongNameResponseDto> getDongCodeByDongName(
            @ApiParam(value = "시도명", required = true) String sidoName,
            @ApiParam(value = "시군구명", required = true) String gugunName,
            @ApiParam(value = "동명", required = true) String dongName
    ) {
        GetDongCodeByDongNameResponseDto res = new GetDongCodeByDongNameResponseDto();

        label:
        try {
            String dongCode = houseService.getDongCodeByDongName(sidoName, gugunName, dongName);
            logger.debug("dongCode: {}", dongCode);
            if (dongCode == null) {
                res.setStatus(400);
                res.setMessage("동 코드 조회 실패. 정보를 정확히 입력했는지 다시 한 번 확인해보세요.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("동 코드 조회 성공");
            res.setData(dongCode);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("동 코드 조회 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "getHouseInfosByDongCode", notes = "동 코드로 아파트 목록 조회")
    @GetMapping("/houseinfo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아파트 목록 조회 성공", response = GetHouseInfosByDongCodeResponseDto.class),
            @ApiResponse(code = 400, message = "아파트 목록 조회 실패", response = GetHouseInfosByDongCodeResponseDto.class),
            @ApiResponse(code = 500, message = "아파트 목록 조회 실패", response = GetHouseInfosByDongCodeResponseDto.class)
    })
    public ResponseEntity<GetHouseInfosByDongCodeResponseDto> getHouseInfosByDongCode(
            @ApiParam(value = "동 코드", required = true) String dongCode
    ) {
        GetHouseInfosByDongCodeResponseDto res = new GetHouseInfosByDongCodeResponseDto();

        label:
        try {
            List<HouseInfoDto> list = houseService.getHouseInfosByDongCode(dongCode);
            logger.debug("아파트 목록: {}", list.size());
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("아파트 목록 조회 실패.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("아파트 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("아파트 목록 조회 실패");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @ApiOperation(value = "getHouseInfoDealsByDongCode", notes = "동 코드로 아파트 최근 거래 목록 조회. 기본은 최근순(아이디 역순)")
    @GetMapping("/houseinfodeal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아파트 최근 거래 목록 조회 성공", response = GetHouseInfoDealsByDongCodeResponseDto.class),
            @ApiResponse(code = 400, message = "아파트 최근 거래 목록 조회 실패", response = GetHouseInfoDealsByDongCodeResponseDto.class),
            @ApiResponse(code = 500, message = "아파트 최근 거래 목록 조회 실패", response = GetHouseInfoDealsByDongCodeResponseDto.class)
    })
    public ResponseEntity<GetHouseInfoDealsByDongCodeResponseDto> getHouseInfoDealsByDongCode(
            @ApiParam(value = "동 코드", required = true) String dongCode
    ) {
        GetHouseInfoDealsByDongCodeResponseDto res = new GetHouseInfoDealsByDongCodeResponseDto();

        label:
        try {
            List<HouseInfoDealDto> list = houseService.getHouseInfoDealsByDongCode(dongCode);
            logger.debug("아파트 최근 거래 목록 조회: {}", list.size());
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("아파트 최근 거래 목록 조회 실패.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("아파트 최근 거래 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("아파트 최근 거래 목록 조회 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "getHouseDealsByAptCodeYearMonth", notes = "아파트 번호로 거래 목록 조회. 기본은 최근순(아이디 역순)")
    @GetMapping("/housedeal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아파트 최근 거래 목록 조회 성공", response = GetHouseDealsByAptCodeYearMonthResponseDto.class),
            @ApiResponse(code = 400, message = "아파트 최근 거래 목록 조회 실패", response = GetHouseDealsByAptCodeYearMonthResponseDto.class),
            @ApiResponse(code = 500, message = "아파트 최근 거래 목록 조회 실패", response = GetHouseDealsByAptCodeYearMonthResponseDto.class)
    })
    public ResponseEntity<GetHouseDealsByAptCodeYearMonthResponseDto> getHouseDealsByAptCodeYearMonth(
            @ApiParam(value = "아파트 코드", required = true) String aptCode,
            @ApiParam(value = "연도") String year,
            @ApiParam(value = "월") String month
    ) {
        GetHouseDealsByAptCodeYearMonthResponseDto res = new GetHouseDealsByAptCodeYearMonthResponseDto();

        label:
        try {
            List<HouseDealDto> list = houseService.getHouseDealsByAptCodeYearMonth(aptCode, year, month);
            logger.debug("아파트 최근 거래 목록: {}", list.size());
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("아파트 최근 거래 목록 조회 실패.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("아파트 최근 거래 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("아파트 최근 거래 목록 조회 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "getSidoNames", notes = "시도 이름 목록 조회")
    @GetMapping("/location-name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "시도 이름 목록 조회 성공", response = GetLocationNamesResponseDto.class),
            @ApiResponse(code = 400, message = "시도 이름 목록 조회 실패", response = GetLocationNamesResponseDto.class),
            @ApiResponse(code = 500, message = "시도 이름 목록 조회 실패", response = GetLocationNamesResponseDto.class)
    })
    public ResponseEntity<GetLocationNamesResponseDto> getSidoNames() {
        GetLocationNamesResponseDto res = new GetLocationNamesResponseDto();

        label:
        try {
            List<String> list = houseService.getSidoNames();
            logger.debug("시도 이름 목록: {}", list);
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("시군구 이름 목록 조회 실패.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("시도 이름 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("시도 이름 목록 조회 실패");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @ApiOperation(value = "getGugunNames", notes = "시도에 해당하는 시군구 이름 목록 조회")
    @GetMapping("/location-name/{sidoName}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "시군구 이름 목록 조회 성공", response = GetLocationNamesResponseDto.class),
            @ApiResponse(code = 400, message = "시군구 이름 목록 조회 실패", response = GetLocationNamesResponseDto.class),
            @ApiResponse(code = 500, message = "시군구 이름 목록 조회 실패", response = GetLocationNamesResponseDto.class)
    })
    public ResponseEntity<GetLocationNamesResponseDto> getGugunNames(
            @PathVariable("sidoName") String sidoName
    ) {
        GetLocationNamesResponseDto res = new GetLocationNamesResponseDto();

        label:
        try {
            List<String> list = houseService.getGugunNames(sidoName);
            logger.debug("시군구 이름 목록: {}", list);
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("시군구 이름 목록 조회 실패. 정보를 정확히 입력했는지 다시 한 번 확인해보세요.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("시군구 이름 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("시군구 이름 목록 조회 실패");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @ApiOperation(value = "getDongNames", notes = "시도, 시군구에 해당하는 동 이름 목록 조회")
    @GetMapping("/location-name/{sidoName}/{gugunName}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "동 이름 목록 조회 성공", response = GetLocationNamesResponseDto.class),
            @ApiResponse(code = 400, message = "동 이름 목록 조회 실패", response = GetLocationNamesResponseDto.class)
    })
    public ResponseEntity<GetLocationNamesResponseDto> getDongNames(
            @PathVariable("sidoName") String sidoName,
            @PathVariable("gugunName") String gugunName
    ) {
        GetLocationNamesResponseDto res = new GetLocationNamesResponseDto();

        label:
        try {
            List<String> list = houseService.getDongNames(sidoName, gugunName);
            logger.debug("동 이름 목록: {}", list);
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("동 이름 목록 조회 실패. 정보를 정확히 입력했는지 다시 한 번 확인해보세요.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("동 이름 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("동 이름 목록 조회 실패");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
