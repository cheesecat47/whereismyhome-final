package com.ssafy.whereismyhome.board.controller;

import com.ssafy.whereismyhome.board.model.BoardDto;
import com.ssafy.whereismyhome.board.model.GetNoticesResponseDto;
import com.ssafy.whereismyhome.board.model.WriteArticleRequestDto;
import com.ssafy.whereismyhome.board.model.WriteArticleResponseDto;
import com.ssafy.whereismyhome.board.service.BoardService;
import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"게시판 컨트롤러 API V1"})
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;
    private final MemberService memberService;

    public BoardController(BoardService boardService, MemberService memberService) {
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @ApiOperation(value = "writeArticle", notes = "글 생성")
    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 생성 성공", response = WriteArticleResponseDto.class),
            @ApiResponse(code = 401, message = "글 생성 실패", response = WriteArticleResponseDto.class),
            @ApiResponse(code = 500, message = "글 생성 실패", response = WriteArticleResponseDto.class)
    })
    public ResponseEntity<WriteArticleResponseDto> writeArticle(
            @RequestBody WriteArticleRequestDto dto
    ) {
        WriteArticleResponseDto res = new WriteArticleResponseDto();

        label:
        try {
            logger.debug("dto: {}", dto);

            // 빈 문자열인지 체크
            String title = dto.getTitle();
            String content = dto.getContent();
            if (title == null || content == null || title.equals("") || content.equals("")) {
                res.setStatus(400);
                res.setMessage("글 생성 실패: 제목, 내용은 필수입니다.");
                res.setData(dto);
                break label;
            }

            // 작성자 아이디 확인
            int memberId = dto.getMemberId();
            MemberDto member = memberService.getMemberById(memberId);
            if (member == null) {
                res.setStatus(400);
                res.setMessage("글 생성 실패: 사용자 아이디를 다시 한 번 확인하세요.");
                res.setData(dto);
                break label;
            }

            // 글 작성
            BoardDto boardDto = new BoardDto();
            boardDto.setTitle(dto.getTitle());
            boardDto.setContent(dto.getContent());
            boardDto.setType(0);
            boardDto.setMemberId(memberId);
            // 동 코드가 빈 칸이라면 작성자의 지역으로 자동 설정.
            boardDto.setDongCode(dto.getDongCode() == null ? member.getDongCode() : dto.getDongCode());

            int cnt = boardService.writeArticle(boardDto);
            assert cnt == 1;
            logger.info("글 생성 성공: {}", cnt);

            res.setStatus(201);
            res.setMessage("글 생성 성공");
        } catch (Exception e) {
            logger.error("글 생성 실패: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("글 생성 실패");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getNotices", notes = "전체 공지글 목록 조회")
    @GetMapping("/notice")
    @ApiResponses({
            @ApiResponse(code = 200, message = "공지글 목록 조회 성공", response = GetNoticesResponseDto.class),
            @ApiResponse(code = 400, message = "공지글 목록 조회 실패", response = GetNoticesResponseDto.class)
    })
    public ResponseEntity<GetNoticesResponseDto> getNotices() {
        GetNoticesResponseDto res = new GetNoticesResponseDto();

        label:
        try {
            List<BoardDto> list = boardService.getNotices();
            logger.debug("공지글 목록: {}", list);
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("공지글 목록 조회 실패.");
                break label;
            }

            res.setStatus(200);
            res.setMessage("공지글 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("공지글 목록 조회 실패.");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
