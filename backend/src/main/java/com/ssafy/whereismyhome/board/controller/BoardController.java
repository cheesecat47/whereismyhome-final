package com.ssafy.whereismyhome.board.controller;

import com.ssafy.whereismyhome.board.model.*;
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
            @ApiResponse(code = 201, message = "글 생성 성공", response = WriteArticleResponseDto.class),
            @ApiResponse(code = 401, message = "글 생성 실패", response = WriteArticleResponseDto.class),
            @ApiResponse(code = 500, message = "글 생성 실패", response = WriteArticleResponseDto.class)
    })
    public ResponseEntity<WriteArticleResponseDto> writeArticle(
            @RequestBody WriteArticleRequestDto dto
    ) {
        WriteArticleResponseDto res = new WriteArticleResponseDto();

        label:
        try {
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
            if (cnt != 1) {
                res.setStatus(400);
                res.setMessage("글 생성 실패");
                break label;
            }
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
            @ApiResponse(code = 400, message = "공지글 목록 조회 실패", response = GetNoticesResponseDto.class),
            @ApiResponse(code = 500, message = "공지글 목록 조회 실패", response = GetNoticesResponseDto.class)
    })
    public ResponseEntity<GetNoticesResponseDto> getNotices() {
        GetNoticesResponseDto res = new GetNoticesResponseDto();

        label:
        try {
            List<BoardDto> list = boardService.getNotices();
            logger.debug("공지글 목록: {}", list);
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("공지글 목록 조회 실패");
                break label;
            }

            res.setStatus(200);
            res.setMessage("공지글 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("공지글 목록 조회 실패");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getCommunityArticles", notes = "회원 정보에 등록된 동 코드를 사용하여 우리 동네 글 목록 조회")
    @GetMapping("/community/{dongCode}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "동네 글 목록 조회 성공", response = GetNoticesResponseDto.class),
            @ApiResponse(code = 400, message = "동네 글 목록 조회 실패", response = GetNoticesResponseDto.class),
            @ApiResponse(code = 500, message = "동네 글 목록 조회 실패", response = GetNoticesResponseDto.class)
    })
    public ResponseEntity<GetCommunityArticlesResponseDto> getCommunityArticles(
            @PathVariable("dongCode") String dongCode
    ) {
        GetCommunityArticlesResponseDto res = new GetCommunityArticlesResponseDto();

        label:
        try {
            List<BoardDto> list = boardService.getCommunityArticles(dongCode);
            logger.debug("동네 글 목록: {}", list);
            if (list.isEmpty()) {
                res.setStatus(400);
                res.setMessage("동네 글 목록 조회 실패");
                break label;
            }

            res.setStatus(200);
            res.setMessage("동네 글 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("동네 글 목록 조회 실패");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getArticleById", notes = "게시글 아이디에 해당하는 글 조회")
    @GetMapping("/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 조회 성공", response = BoardDto.class),
            @ApiResponse(code = 400, message = "게시글 조회 실패", response = BoardDto.class),
            @ApiResponse(code = 500, message = "게시글 조회 실패", response = BoardDto.class)
    })
    public ResponseEntity<GetArticleByIdResponseDto> getArticleById(
            @PathVariable("boardId") int boardId
    ) {
        GetArticleByIdResponseDto res = new GetArticleByIdResponseDto();

        label:
        try {
            BoardDetailDto article = boardService.getArticleById(boardId);
            logger.debug("게시글: {}", article);
            if (article == null) {
                res.setStatus(400);
                res.setMessage("게시글 조회 실패");
                break label;
            }

            res.setStatus(200);
            res.setMessage("게시글 조회 성공");
            res.setData(article);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("게시글 조회 실패");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "updateArticleById", notes = "게시글 수정")
    @PutMapping("/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 수정 성공", response = UpdateArticleByIdResponseDto.class),
            @ApiResponse(code = 400, message = "게시글 수정 실패", response = UpdateArticleByIdResponseDto.class),
            @ApiResponse(code = 500, message = "게시글 수정 실패", response = UpdateArticleByIdResponseDto.class)
    })
    public ResponseEntity<UpdateArticleByIdResponseDto> updateArticleById(
            @PathVariable("boardId") int boardId,
            @RequestBody UpdateArticleByIdRequestDto dto
    ) {
        UpdateArticleByIdResponseDto res = new UpdateArticleByIdResponseDto();

        label:
        try {
            // 파라미터 유효성 검사
            // 빈 문자열인지 확인
            String newTitle = dto.getTitle();
            String newContent = dto.getContent();
            if ((newTitle != null && newTitle.isEmpty()) || (newContent != null && newContent.isEmpty())) {
                res.setStatus(400);
                res.setMessage("게시글 수정 실패: 빈 문자열은 입력할 수 없습니다.");
                break label;
            }

            // 로그인 된 회원인지 확인
            MemberDto member = memberService.getMemberById(dto.getMemberId());
            if (member == null) {
                res.setStatus(400);
                res.setMessage("게시글 수정 실패: 해당 회원 정보가 존재하지 않습니다.");
                break label;
            }

            // 존재하는 게시글인지 확인
            BoardDetailDto article = boardService.getArticleById(boardId);
            if (article == null) {
                res.setStatus(400);
                res.setMessage("게시글 수정 실패: 해당 게시글이 존재하지 않습니다.");
                break label;
            }

            // 로그인 한 회원이 작성한 글인지 확인
            if (dto.getMemberId() != boardId) {
                res.setStatus(400);
                res.setMessage("게시글 수정 실패: 해당 회원이 작성한 게시글이 아닙니다.");
                break label;
            }

            logger.debug("게시글 수정: 기존 정보 {}", article);
            logger.debug("게시글 수정: 파라미터 {}", dto);

            article.setTitle(newTitle);
            article.setContent(newContent);

            logger.debug("게시글 수정: 수정될 정보 {}", article);
            int cnt = boardService.updateArticleById(article);
            if (cnt != 1) {
                res.setStatus(400);
                res.setMessage("게시글 수정 실패");
                break label;
            }

            logger.info("게시글 수정 성공: {}", cnt);
            res.setStatus(200);
            res.setMessage("게시글 수정 성공");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("게시글 수정 실패");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "deleteArticleById", notes = "게시글 ID에 해당하는 글 삭제")
    @DeleteMapping("/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 삭제 성공", response = DeleteArticleByIdResponseDto.class),
            @ApiResponse(code = 400, message = "게시글 삭제 실패", response = DeleteArticleByIdResponseDto.class),
            @ApiResponse(code = 500, message = "게시글 삭제 실패", response = DeleteArticleByIdResponseDto.class)
    })
    public ResponseEntity<?> deleteArticleById(
            @PathVariable("boardId") int boardId,
            @RequestBody DeleteArticleByIdRequestDto dto
    ) {
        DeleteArticleByIdResponseDto res = new DeleteArticleByIdResponseDto();

        label:
        try {
            // 로그인 된 회원인지 확인
            MemberDto member = memberService.getMemberById(dto.getMemberId());
            if (member == null) {
                res.setStatus(400);
                res.setMessage("게시글 삭제 실패: 해당 회원 정보가 존재하지 않습니다.");
                break label;
            }

            // 존재하는 게시글인지 확인
            BoardDetailDto article = boardService.getArticleById(boardId);
            if (article == null) {
                res.setStatus(400);
                res.setMessage("게시글 삭제 실패: 해당 게시글이 존재하지 않습니다.");
                break label;
            }

            // 로그인 한 회원이 작성한 글인지 확인
            if (dto.getMemberId() != boardId) {
                res.setStatus(400);
                res.setMessage("게시글 삭제 실패: 해당 회원이 작성한 게시글이 아닙니다.");
                break label;
            }

            int cnt = boardService.deleteArticleById(boardId);
            if (cnt != 1) {
                res.setStatus(400);
                res.setMessage("게시글 삭제 실패");
                break label;
            }

            logger.info("게시글 삭제 성공: {}", cnt);
            res.setStatus(200);
            res.setMessage("게시글 삭제 성공");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("게시글 삭제 실패");
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
