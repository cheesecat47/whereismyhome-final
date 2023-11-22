package com.ssafy.whereismyhome.board.controller;

import com.ssafy.whereismyhome.board.model.*;
import com.ssafy.whereismyhome.board.service.BoardService;
import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.service.MemberService;
import com.ssafy.whereismyhome.util.JWTUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = {"게시판 컨트롤러 API V1"})
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @ApiOperation(value = "writeArticle", notes = "글 생성. 로그인 필요.")
    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(code = 201, message = "글 생성 성공", response = WriteArticleResponseDto.class),
            @ApiResponse(code = 401, message = "글 생성 실패", response = WriteArticleResponseDto.class),
            @ApiResponse(code = 500, message = "글 생성 실패", response = WriteArticleResponseDto.class)
    })
    public ResponseEntity<WriteArticleResponseDto> writeArticle(
            @RequestBody WriteArticleRequestDto dto,
            HttpServletRequest request
    ) {
        WriteArticleResponseDto res = new WriteArticleResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "글 생성 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "글 생성 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            // 빈 문자열인지 체크
            String title = dto.getTitle();
            String content = dto.getContent();
            if (title == null || content == null || title.equals("") || content.equals("")) {
                msg = "글 생성 실패: 제목, 내용은 필수입니다";
                logger.info("{}: {} {}", msg, title, content);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(dto);
                break label;
            }

            // 작성자 아이디 확인
            int memberId = dto.getMemberId();
            int tokenMemberId = jwtUtil.getMemberId(token);
            if (memberId != tokenMemberId) {
                // 로그인한 사용자와 글 작성자가 다른 경우
                msg = "글 생성 실패: 허용되지 않은 접근입니다.";
                logger.info("{}: {} {}", msg, memberId, tokenMemberId);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(dto);
                break label;
            }

            MemberDto member = memberService.getMemberById(memberId);

            // 글 작성
            BoardDetailDto boardDto = new BoardDetailDto();
            boardDto.setTitle(dto.getTitle());
            boardDto.setContent(dto.getContent());
            boardDto.setType(0);
            boardDto.setMemberId(memberId);
            // 동 코드가 빈 칸이라면 작성자의 지역으로 자동 설정.
            boardDto.setDongCode(dto.getDongCode() == null ? member.getDongCode() : dto.getDongCode());
            logger.debug("boardDto: {}", boardDto);

            int cnt = boardService.writeArticle(boardDto);
            if (cnt != 1) {
                msg = "글 생성 실패";
                logger.info("{}: {}", msg, cnt);
                res.setStatus(400);
                res.setMessage(msg);
                break label;
            }

            msg = "글 생성 성공";
            logger.info("{}: {}", msg, cnt);
            res.setStatus(201);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "글 생성 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
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
    public ResponseEntity<GetNoticesResponseDto> getNotices(
            @RequestParam(required = false) @ApiParam(value = "검색 조건(title: 제목, memberName: 작성자명, content: 내용)") String searchBy,
            @RequestParam(required = false) @ApiParam(value = "검색 키워드. 한글, 영문자, 숫자, 공백(space)으로 10자 이내 가능.") String searchKeyword,
            @RequestParam(required = false, defaultValue = "desc") @ApiParam(value = "정렬 방법(asc, desc). 기본은 최근순(desc).") String orderBy
    ) {
        GetNoticesResponseDto res = new GetNoticesResponseDto();
        String msg;

        label:
        try {
            // 검색 조건 없이 키워드만 입력한 경우 확인
            if (searchBy == null && searchKeyword != null) {
                msg = "공지글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요";
                logger.info("{}: searchBy: {}, searchKeyword: {}", msg, searchBy, searchKeyword);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("searchBy", null);
                    put("searchKeyword", searchKeyword);
                }});
                break label;
            }

            // 검색 조건 확인
            if (searchBy != null && !searchBy.matches("title|memberName|content")) {
                msg = "공지글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요";
                logger.info("{}: searchBy: {}", msg, searchBy);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("searchBy", searchBy);
                    put("searchKeyword", searchKeyword);
                }});
                break label;
            }

            // 검색 조건 확인
            if (searchKeyword != null && !searchKeyword.matches("^[\\w가-힣 ]{1,10}$")) {
                msg = "공지글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요";
                logger.info("{}: searchBy: {}", msg, searchBy);
                res.setStatus(400);
                res.setMessage("공지글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요.");
                res.setData(new HashMap<String, Object>() {{
                    put("searchBy", searchBy);
                    put("searchKeyword", searchKeyword);
                }});
                break label;
            }

            // orderBy 키워드가 적절히 들어왔는지 확인.
            if (!orderBy.matches("asc|desc")) {
                msg = "공지글 목록 조회 실패: 정렬 방식을 다시 한 번 확인해주세요";
                logger.info("{}: orderBy: {}", msg, orderBy);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("orderBy", orderBy);
                }});
                break label;
            }

            logger.debug("공지글 목록 조회: searchBy: {} / searchKeyword: {} / orderBy: {}", searchBy, searchKeyword, orderBy);

            List<BoardListDto> list = boardService.getNotices(searchBy, searchKeyword, orderBy);

            msg = "공지글 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "공지글 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
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
            @PathVariable("dongCode") String dongCode,
            @RequestParam(required = false) @ApiParam(value = "검색 조건(title: 제목, memberName: 작성자명, content: 내용)") String searchBy,
            @RequestParam(required = false) @ApiParam(value = "검색 키워드. 한글, 영문자, 숫자, 공백(space)으로 10자 이내 가능.") String searchKeyword,
            @RequestParam(required = false, defaultValue = "desc") @ApiParam(value = "정렬 방법(asc, desc). 기본은 최근순(desc).") String orderBy
    ) {
        GetCommunityArticlesResponseDto res = new GetCommunityArticlesResponseDto();
        String msg;

        label:
        try {
            // 검색 조건 없이 키워드만 입력한 경우 확인
            if (searchBy == null && searchKeyword != null) {
                msg = "동네 글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요";
                logger.info("{}: searchBy: {}, searchKeyword: {}", msg, searchBy, searchKeyword);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("searchBy", null);
                    put("searchKeyword", searchKeyword);
                }});
                break label;
            }

            // 검색 조건 확인
            if (searchBy != null && !searchBy.matches("title|memberName|content")) {
                msg = "동네 글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요";
                logger.debug("{}: searchBy: {}", msg, searchBy);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("searchBy", searchBy);
                    put("searchKeyword", searchKeyword);
                }});
                break label;
            }

            // 검색 조건 확인
            if (searchKeyword != null && !searchKeyword.matches("^[\\w가-힣 ]{1,10}$")) {
                msg = "동네 글 목록 조회 실패: 검색 조건을 다시 한 번 확인해주세요";
                logger.debug("{}: searchBy: {}", msg, searchBy);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("searchBy", searchBy);
                    put("searchKeyword", searchKeyword);
                }});
                break label;
            }

            // orderBy 키워드가 적절히 들어왔는지 확인.
            if (orderBy != null && !orderBy.matches("asc|desc")) {
                msg = "동네 글 목록 조회 실패: 정렬 방식을 다시 한 번 확인해주세요";
                logger.debug("{}: orderBy: {}", msg, orderBy);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("orderBy", orderBy);
                }});
                break label;
            }

            logger.debug("동네 글 목록 조회: searchBy: {} / searchKeyword: {} / orderBy: {}", searchBy, searchKeyword, orderBy);

            List<BoardListDto> list = boardService.getCommunityArticles(dongCode, searchBy, searchKeyword, orderBy);

            msg = "동네 글 목록 조회 성공";
            logger.info("{}: {}", msg, list);
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "동네 글 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "getArticleById", notes = "게시글 아이디에 해당하는 글 조회")
    @GetMapping("/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 조회 성공", response = BoardListDto.class),
            @ApiResponse(code = 400, message = "게시글 조회 실패", response = BoardListDto.class),
            @ApiResponse(code = 500, message = "게시글 조회 실패", response = BoardListDto.class)
    })
    public ResponseEntity<GetArticleByIdResponseDto> getArticleById(
            @PathVariable("boardId") int boardId
    ) {
        GetArticleByIdResponseDto res = new GetArticleByIdResponseDto();
        String msg;

        label:
        try {
            BoardDetailDto article = boardService.getArticleById(boardId);
            logger.debug("게시글: {}", article);
            if (article == null) {
                msg = "게시글 조회 실패: ID에 해당하는 글이 존재하지 않습니다.";
                logger.info("{}: {}", msg, boardId);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("boardId", boardId);
                }});
                break label;
            }

            msg = "게시글 조회 성공";
            logger.info("{}: {}", msg, article);
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(article);
        } catch (Exception e) {
            msg = "게시글 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "updateArticleById", notes = "게시글 수정. 로그인 필요.")
    @PutMapping("/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 수정 성공", response = UpdateArticleByIdResponseDto.class),
            @ApiResponse(code = 400, message = "게시글 수정 실패", response = UpdateArticleByIdResponseDto.class),
            @ApiResponse(code = 500, message = "게시글 수정 실패", response = UpdateArticleByIdResponseDto.class)
    })
    public ResponseEntity<UpdateArticleByIdResponseDto> updateArticleById(
            @PathVariable("boardId") int boardId,
            @RequestBody UpdateArticleByIdRequestDto dto,
            HttpServletRequest request
    ) {
        UpdateArticleByIdResponseDto res = new UpdateArticleByIdResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "게시글 수정 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "게시글 수정 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            // 파라미터 유효성 검사
            // 빈 문자열인지 확인
            String newTitle = dto.getTitle();
            String newContent = dto.getContent();
            if ((newTitle != null && newTitle.isEmpty()) || (newContent != null && newContent.isEmpty())) {
                msg = "게시글 수정 실패: 빈 문자열은 입력할 수 없습니다";
                logger.debug("{}: newTitle: {}, newContent: {}", msg, newTitle, newContent);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(dto);
                break label;
            }

            // 존재하는 게시글인지 확인
            BoardDetailDto article = boardService.getArticleById(boardId);
            if (article == null) {
                msg = "게시글 수정 실패: 해당 게시글이 존재하지 않습니다";
                logger.info("{}: {}", msg, boardId);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("boardId", boardId);
                }});
                break label;
            }

            // 로그인 한 회원이 작성한 글인지 확인
            int articleMemberId = article.getMemberId();
            int tokenMemberId = jwtUtil.getMemberId(token);
            if (articleMemberId != tokenMemberId) {
                msg = "게시글 수정 실패: 해당 회원이 작성한 게시글이 아닙니다";
                logger.info("{}: {} {}", msg, articleMemberId, tokenMemberId);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("article.memberId", articleMemberId);
                    put("tokenMemberId", tokenMemberId);
                }});
                break label;
            }

            logger.debug("게시글 수정: 기존 정보 {}", article);
            logger.debug("게시글 수정: 파라미터 {}", dto);

            article.setTitle(newTitle);
            article.setContent(newContent);

            logger.debug("게시글 수정: 수정될 정보 {}", article);
            int cnt = boardService.updateArticleById(article);
            if (cnt != 1) {
                msg = "게시글 수정 실패";
                logger.info("{}: {}", msg, cnt);
                res.setStatus(400);
                res.setMessage(msg);
                break label;
            }

            msg = "게시글 수정 성공";
            logger.info("{}: {}", msg, cnt);
            res.setStatus(200);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "게시글 수정 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "deleteArticleById", notes = "게시글 ID에 해당하는 글 삭제. 로그인 필요.")
    @DeleteMapping("/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 삭제 성공", response = DeleteArticleByIdResponseDto.class),
            @ApiResponse(code = 400, message = "게시글 삭제 실패", response = DeleteArticleByIdResponseDto.class),
            @ApiResponse(code = 500, message = "게시글 삭제 실패", response = DeleteArticleByIdResponseDto.class)
    })
    public ResponseEntity<?> deleteArticleById(
            @PathVariable("boardId") int boardId,
            HttpServletRequest request
    ) {
        DeleteArticleByIdResponseDto res = new DeleteArticleByIdResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "게시글 삭제 실패: 로그인이 필요한 서비스입니다.";
                logger.info("{}: {}", msg, token);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "게시글 삭제 실패: 사용 불가능한 토큰입니다.";
                logger.info("{}: {}", msg, token);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            // 존재하는 게시글인지 확인
            BoardDetailDto article = boardService.getArticleById(boardId);
            if (article == null) {
                msg = "게시글 삭제 실패: 해당 게시글이 존재하지 않습니다.";
                logger.info("{}: {}", msg, boardId);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("boardId", boardId);
                }});
                break label;
            }

            // 로그인 한 회원이 작성한 글인지 확인
            int tokenMemberId = jwtUtil.getMemberId(token);
            int articleMemberId = article.getMemberId();
            if (tokenMemberId != articleMemberId) {
                msg = "게시글 삭제 실패: 해당 회원이 작성한 게시글이 아닙니다";
                logger.info("{}: {} {}", msg, articleMemberId, tokenMemberId);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("article.memberId", articleMemberId);
                    put("tokenMemberId", tokenMemberId);
                }});
                break label;
            }

            int cnt = boardService.deleteArticleById(boardId);
            if (cnt != 1) {
                msg = "게시글 삭제 실패";
                logger.info("{}: {}", msg, cnt);
                res.setStatus(400);
                res.setMessage(msg);
                break label;
            }

            msg = "게시글 삭제 성공";
            logger.info("{}: {}", msg, cnt);
            res.setStatus(200);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "게시글 삭제 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
