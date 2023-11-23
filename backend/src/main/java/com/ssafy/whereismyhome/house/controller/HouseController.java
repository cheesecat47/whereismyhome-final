package com.ssafy.whereismyhome.house.controller;

import com.ssafy.whereismyhome.house.model.*;
import com.ssafy.whereismyhome.house.service.HouseService;
import com.ssafy.whereismyhome.util.JWTUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(value = "House", tags = {"아파트 정보 API V1"})
@RequestMapping("/house")
@Slf4j
@RequiredArgsConstructor
public class HouseController {

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);

    private final HouseService houseService;

    private final JWTUtil jwtUtil;

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
        String msg;

        label:
        try {
            // 파라미터 유효성 검사
            logger.debug("sidoName: {} / gugunName: {} / dongName: {}", sidoName, gugunName, dongName);

            // 빈 문자열인지, 한글만 있는지 체크
            if (sidoName == null || gugunName == null || dongName == null || !sidoName.matches("[가-힣]+") || !gugunName.matches("[가-힣]+") || !dongName.matches("[가-힣]+")) {
                msg = "동 코드 조회 실패: 입력 값을 다시 한 번 확인해주세요";
                logger.info("{}: {} {} {}", msg, sidoName, gugunName, dongName);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("sidoName", sidoName);
                    put("gugunName", gugunName);
                    put("dongName", dongName);
                }});
                break label;
            }

            // 동 코드 조회
            String dongCode = houseService.getDongCodeByDongName(sidoName, gugunName, dongName);
            logger.debug("dongCode: {}", dongCode);
            if (dongCode == null) {
                msg = "동 코드 조회 실패. 정보를 정확히 입력했는지 다시 한 번 확인해보세요";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("sidoName", sidoName);
                    put("gugunName", gugunName);
                    put("dongName", dongName);
                }});
                break label;
            }

            msg = "동 코드 조회 성공";
            logger.info(msg);
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(dongCode);
        } catch (Exception e) {
            msg = "동 코드 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getHouseInfosByDongCode", notes = "동 코드로 아파트 목록 조회. 로그인 된 상태면 동 조회 기록 남김.")
    @GetMapping("/houseinfo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아파트 목록 조회 성공", response = GetHouseInfosByDongCodeResponseDto.class),
            @ApiResponse(code = 400, message = "아파트 목록 조회 실패", response = GetHouseInfosByDongCodeResponseDto.class),
            @ApiResponse(code = 500, message = "아파트 목록 조회 실패", response = GetHouseInfosByDongCodeResponseDto.class)
    })
    public ResponseEntity<GetHouseInfosByDongCodeResponseDto> getHouseInfosByDongCode(
            @ApiParam(value = "동 코드", required = true) String dongCode,
            HttpServletRequest request
    ) {
        GetHouseInfosByDongCodeResponseDto res = new GetHouseInfosByDongCodeResponseDto();
        String msg;

        label:
        try {
            // 파라미터 유효성 검사
            logger.debug("dongCode: {}", dongCode);

            if (!dongCode.matches("\\d{10}")) {
                msg = "아파트 목록 조회 실패: 동 코드 형식이 유효하지 않습니다";
                logger.info("{}: {}", msg, dongCode);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("dongCode", dongCode);
                }});
                break label;
            }

            // 로그인 된 상태이면 이 동네 정보를 조회했다고 history에 추가
            String token = request.getHeader("Authorization");
            if (token != null) {
                // 이 API는 로그인 된 상태 아니더라도 이용 가능하므로 토큰이 만료되었다면 카운트만 안 올리면 됨.
                if (jwtUtil.checkToken(token)) {
                    int memberId = jwtUtil.getMemberId(token);
                    houseService.addDongViewHistory(String.valueOf(memberId), dongCode);
                    logger.debug("동 조회수 카운트: {} {}", memberId, dongCode);
                }
            }

            // TODO: 로그인 된 상태면 내가 좋아요를 눌렀는지 여부도 포함 필요.
            List<HouseInfoDto> list = houseService.getHouseInfosByDongCode(dongCode);

            msg = "아파트 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "아파트 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
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
        String msg;

        label:
        try {
            // 파라미터 유효성 검사
            logger.debug("dongCode: {}", dongCode);

            if (!dongCode.matches("\\d{10}")) {
                msg = "아파트 최근 거래 목록 조회 실패: 동 코드 형식이 유효하지 않습니다";
                logger.info("{}: {}", msg, dongCode);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("dongCode", dongCode);
                }});
                break label;
            }

            List<HouseInfoDealDto> list = houseService.getHouseInfoDealsByDongCode(dongCode);

            msg = "아파트 최근 거래 목록 조회 성공";
            logger.info("{}: {}", msg, dongCode);
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "아파트 최근 거래 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getHouseDealsByAptCodeYearMonth", notes = "아파트 번호로 거래 목록 조회. 기본은 최근순(아이디 역순). 로그인 된 상태면 아파트 조회 기록 남김.")
    @GetMapping("/housedeal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아파트 최근 거래 목록 조회 성공", response = GetHouseDealsByAptCodeYearMonthResponseDto.class),
            @ApiResponse(code = 400, message = "아파트 최근 거래 목록 조회 실패", response = GetHouseDealsByAptCodeYearMonthResponseDto.class),
            @ApiResponse(code = 500, message = "아파트 최근 거래 목록 조회 실패", response = GetHouseDealsByAptCodeYearMonthResponseDto.class)
    })
    public ResponseEntity<GetHouseDealsByAptCodeYearMonthResponseDto> getHouseDealsByAptCodeYearMonth(
            @ApiParam(value = "아파트 코드", required = true) String aptCode,
            @ApiParam(value = "연도(4자리)") String year,
            @ApiParam(value = "월(2자리, 0포함)") String month,
            HttpServletRequest request
    ) {
        GetHouseDealsByAptCodeYearMonthResponseDto res = new GetHouseDealsByAptCodeYearMonthResponseDto();
        String msg;

        label:
        try {
            // 파라미터 유효성 검사
            logger.debug("aptCode: {} / year: {} / month: {}", aptCode, year, month);

            if (aptCode == null || !aptCode.matches("\\d{14}") || (year != null && !year.matches("\\d{4}")) || (month != null && !month.matches("\\d{2}"))) {
                msg = "아파트 최근 거래 목록 조회 실패: 입력값 형식이 유효하지 않습니다";
                logger.info("{}: {}", msg, aptCode);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("aptCode", aptCode);
                    put("year", year);
                    put("month", month);
                }});
                break label;
            }

            // 로그인 된 상태이면 이 아파트 정보를 조회했다고 history에 추가
            String token = request.getHeader("Authorization");
            if (token != null) {
                // 이 API는 로그인 된 상태 아니더라도 이용 가능하므로 토큰이 만료되었다면 카운트만 안 올리면 됨.
                if (jwtUtil.checkToken(token)) {
                    int memberId = jwtUtil.getMemberId(token);
                    houseService.addAptViewHistory(String.valueOf(memberId), aptCode);
                    logger.debug("아파트 조회수 카운트: {} {}", memberId, aptCode);
                }
            }

            List<HouseDealDto> list = houseService.getHouseDealsByAptCodeYearMonth(aptCode, year, month);

            msg = "아파트 최근 거래 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "아파트 최근 거래 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getSidoNames", notes = "시도 이름 목록 조회")
    @GetMapping("/location-name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "시도 이름 목록 조회 성공", response = GetLocationNamesResponseDto.class),
            @ApiResponse(code = 500, message = "시도 이름 목록 조회 실패", response = GetLocationNamesResponseDto.class)
    })
    public ResponseEntity<GetLocationNamesResponseDto> getSidoNames() {
        GetLocationNamesResponseDto res = new GetLocationNamesResponseDto();
        String msg;

        try {
            List<String> list = houseService.getSidoNames();

            msg = "시도 이름 목록 조회 성공";
            logger.debug("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "시도 이름 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
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
        String msg;

        label:
        try {
            // 파라미터 유효성 검사
            logger.debug("sidoName: {}", sidoName);

            // 빈 문자열인지, 한글만 있는지 체크
            if (sidoName == null || !sidoName.matches("[가-힣]+")) {
                msg = "시군구 이름 목록 조회 실패: 정보를 정확히 입력했는지 다시 한 번 확인해주세요";
                logger.info("{}: {}", msg, sidoName);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("sidoName", sidoName);
                }});
                break label;
            }

            List<String> list = houseService.getGugunNames(sidoName);
            if (list.isEmpty()) {
                msg = "시군구 이름 목록 조회 실패: 정보를 정확히 입력했는지 다시 한 번 확인해주세요";
                logger.info("{}: {}", msg, sidoName);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("sidoName", sidoName);
                }});
                break label;
            }

            msg = "시군구 이름 목록 조회 성공";
            logger.debug("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "시군구 이름 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
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
        String msg;

        label:
        try {
            // 파라미터 유효성 검사
            logger.debug("sidoName: {} / gugunName: {}", sidoName, gugunName);

            // 빈 문자열인지, 한글만 있는지 체크
            if (sidoName == null || gugunName == null || !sidoName.matches("[가-힣]+") || !gugunName.matches("[가-힣]+")) {
                msg = "동 이름 목록 조회 실패: 정보를 정확히 입력했는지 다시 한 번 확인해주세요";
                logger.info("{}: {} {}", msg, sidoName, gugunName);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("sidoName", sidoName);
                    put("gugunName", gugunName);
                }});
                break label;
            }

            List<String> list = houseService.getDongNames(sidoName, gugunName);
            logger.debug("동 이름 목록: {}", list);
            if (list.isEmpty()) {
                msg = "동 이름 목록 조회 실패: 정보를 정확히 입력했는지 다시 한 번 확인해주세요";
                logger.info("{}: {} {}", msg, sidoName, gugunName);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("sidoName", sidoName);
                    put("gugunName", gugunName);
                }});
                break label;
            }

            msg = "동 이름 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "동 이름 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(400);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
