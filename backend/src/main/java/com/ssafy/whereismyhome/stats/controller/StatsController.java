package com.ssafy.whereismyhome.stats.controller;

import com.ssafy.whereismyhome.stats.model.FavouriteAptDto;
import com.ssafy.whereismyhome.stats.model.FavouriteDongDto;
import com.ssafy.whereismyhome.stats.model.GetFavouriteTop5ResponseDto;
import com.ssafy.whereismyhome.stats.service.StatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"통계 정보 컨트롤러 API V1"})
@RequestMapping("/stats")
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);
    private final StatsService statsService;

    @ApiOperation(value = "getFavouriteDongTop5", notes = "최근 7일간 인기 있는 (조회수가 많은) 동 top 5 이름과 조회수 조회")
    @GetMapping("/favourite/dong")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인기 있는 동 목록 조회 성공", response = GetFavouriteTop5ResponseDto.class),
            @ApiResponse(code = 500, message = "인기 있는 동 목록 조회 실패", response = GetFavouriteTop5ResponseDto.class)
    })
    public ResponseEntity<GetFavouriteTop5ResponseDto> getFavouriteDongTop5() {
        GetFavouriteTop5ResponseDto res = new GetFavouriteTop5ResponseDto();
        String msg;

        try {
            List<FavouriteDongDto> list = statsService.getFavouriteDongTop5();
            msg = "인기 있는 동 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "인기 있는 동 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getFavouriteAptTop5", notes = "최근 7일간 인기 있는 (조회수가 많은) 아파트 top 5 이름과 조회수 조회")
    @GetMapping("/favourite/apartment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인기 있는 아파트 목록 조회 성공", response = GetFavouriteTop5ResponseDto.class),
            @ApiResponse(code = 500, message = "인기 있는 아파트 목록 조회 실패", response = GetFavouriteTop5ResponseDto.class)
    })
    public ResponseEntity<GetFavouriteTop5ResponseDto> getFavouriteAptTop5() {
        GetFavouriteTop5ResponseDto res = new GetFavouriteTop5ResponseDto();
        String msg;

        try {
            List<FavouriteAptDto> list = statsService.getFavouriteAptTop5();
            msg = "인기 있는 아파트 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "인기 있는 아파트 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
