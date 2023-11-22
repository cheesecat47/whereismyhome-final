package com.ssafy.whereismyhome.member.controller;

import com.ssafy.whereismyhome.member.model.*;
import com.ssafy.whereismyhome.member.service.MemberService;
import com.ssafy.whereismyhome.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Api(tags = {"멤버 컨트롤러  API V1"})
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    private final JWTUtil jwtUtil;

    @ApiOperation(value = "로그인", notes = "회원 이메일과 비밀번호를 입력 받아 로그인 처리")
    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "로그인 실패", response = LoginResponseDto.class),
            @ApiResponse(code = 500, message = "로그인 실패", response = LoginResponseDto.class)
    })
    public ResponseEntity<LoginResponseDto> loginMember(
            @RequestBody LoginRequestDto params
    ) {
        LoginResponseDto res = new LoginResponseDto();
        String msg;

        label:
        try {
            // 로그인 파라미터 유효성 검사
            logger.debug("params: {}", params);

            // 빈 문자열인지 체크
            String email = params.getEmail();
            String password = params.getPassword();
            if (email == null || password == null || email.equals("") || password.equals("")) {
                msg = "로그인 실패: 아이디, 비밀번호는 필수입니다";
                logger.info("{}: {}", msg, params);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(params);
                break label;
            }

            // 이메일 형식 검사
            Matcher matcher = Pattern
                    .compile("^(?<emailAccount>[a-zA-Z0-9]{1,50})@(?<emailDomain>(ssafy|gmail)\\.com)$")
                    .matcher(email);
            if (!matcher.matches()) {
                msg = "로그인 실패: 이메일 형식이 유효하지 않습니다";
                logger.info("{}: {}", msg, params);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(params);
                break label;
            }

            // 유효성 검사는 통과했고, 이제부터는 DB에 존재하는 유저면 로그인 성공, 아니면 실패.
            String emailAccount = matcher.group("emailAccount");
            String emailDomain = matcher.group("emailDomain");
            logger.debug("emailAccount: {} / emailDomain: {}", emailAccount, emailDomain);

            MemberDto member = memberService.loginMember(emailAccount, emailDomain, params.getPassword());
            if (member == null) {
                msg = "로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다";
                logger.info("{}: {}", msg, params);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(params);
                break label;
            }

            // 로그인 성공했으면 토큰 발급
            String accessToken = jwtUtil.createAccessToken(member.getMemberId(), email);
            String refreshToken = jwtUtil.createRefreshToken(member.getMemberId(), email);
            member.setRefreshToken(refreshToken);
            logger.debug("accessToken: {} / refreshToken: {}", accessToken, refreshToken);

            // 발급 받은 refresh token을 DB에 저장
            int cnt = memberService.updateRefreshToken(String.valueOf(member.getMemberId()), refreshToken);
            if (cnt != 1) {
                msg = "로그인 실패: 토큰 발행 중 오류가 발생했습니다";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(params);
                break label;
            }

            msg = "로그인 성공";
            logger.info("{}: {}", msg, member);
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(new HashMap<String, Object>() {{
                put("memberInfo", member);
                put("access-token", accessToken);
                put("refresh-token", refreshToken);
            }});
        } catch (Exception e) {
            msg = "로그인 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
            res.setData(null);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "logoutMember", notes = "회원 아이디가 일치하는 유저 로그아웃 처리")
    @PostMapping("/logout/{memberId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공", response = LogoutResponseDto.class),
            @ApiResponse(code = 400, message = "로그아웃 실패", response = LogoutResponseDto.class),
            @ApiResponse(code = 401, message = "로그아웃 실패", response = LogoutResponseDto.class),
            @ApiResponse(code = 500, message = "로그아웃 실패", response = LogoutResponseDto.class)
    })
    public ResponseEntity<LogoutResponseDto> logoutMember(
            @PathVariable("memberId") int memberId,
            HttpServletRequest request
    ) {
        LogoutResponseDto res = new LogoutResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "로그아웃 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "로그아웃 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            // 로그인 한 유저가 자신과 관련된 요청을 하는지 확인
            int tokenMemberId = jwtUtil.getMemberId(token);
            if (memberId != tokenMemberId) {
                msg = "로그아웃 실패: 허용되지 않은 접근입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                    put("tokenMemberId", tokenMemberId);
                }});
                break label;
            }

            int cnt = memberService.updateRefreshToken(String.valueOf(memberId), null);
            if (cnt != 1) {
                msg = "로그아웃 실패: 토큰 업데이트 중 오류가 발생했습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                }});
                break label;
            }

            msg = "로그아웃 성공";
            logger.info(msg);
            res.setStatus(200);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "로그아웃 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "refreshToken", notes = "만료된 Access Token 재발급")
    @PostMapping("/refresh/{memberId}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "토큰 재발급 성공", response = RefreshTokenResponseDto.class),
            @ApiResponse(code = 400, message = "토큰 재발급 실패", response = RefreshTokenResponseDto.class),
            @ApiResponse(code = 401, message = "토큰 재발급 실패", response = RefreshTokenResponseDto.class),
            @ApiResponse(code = 500, message = "토큰 재발급 실패", response = RefreshTokenResponseDto.class)
    })
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(
            @PathVariable("memberId") int memberId,
            HttpServletRequest request
    ) {
        RefreshTokenResponseDto res = new RefreshTokenResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String refreshToken = request.getHeader("Refresh");
            if (refreshToken == null) {
                msg = "토큰 재발급 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(refreshToken)) {
                msg = "토큰 재발급 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Refresh", refreshToken);
                }});
                break label;
            }

            // 로그인 한 유저가 자신과 관련된 요청을 하는지 확인
            int tokenMemberId = jwtUtil.getMemberId(refreshToken);
            if (memberId != tokenMemberId) {
                msg = "토큰 재발급 실패: 허용되지 않은 접근입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                    put("tokenMemberId", tokenMemberId);
                }});
                break label;
            }

            MemberDto member = memberService.getMemberById(memberId);
            if (member == null) {
                msg = "토큰 재발급 실패: 해당하는 멤버가 존재하지 않습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                }});
                break label;
            }
            String memberEmail = String.format("%s@%s", member.getEmailAccount(), member.getEmailDomain());

            String newAccessToken = jwtUtil.createAccessToken(memberId, memberEmail);
            logger.debug("newAccessToken: {}", newAccessToken);

            msg = "토큰 재발급 성공";
            logger.info("{}: {}", msg, newAccessToken);
            res.setStatus(201);
            res.setMessage(msg);
            res.setData(new HashMap<String, Object>() {{
                put("access-token", newAccessToken);
            }});
        } catch (Exception e) {
            msg = "토큰 재발급 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "회원 등록", notes = "회원 정보를 입력 받아 회원 가입 처리")
    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 등록 성공", response = SignUpMemberResponseDto.class),
            @ApiResponse(code = 400, message = "회원 등록 실패", response = SignUpMemberResponseDto.class)
    })
    public ResponseEntity<SignUpMemberResponseDto> signUpMember(
            @RequestBody SignUpMemberRequestDto dto
    ) {
        SignUpMemberResponseDto res = new SignUpMemberResponseDto();
        String msg;

        label:
        try {
            // TODO: 아이디, 이메일 중복 검사.
            // TODO: 사용자에게 입력 받은 주소에 해당하는 동코드가 존재하는지 확인.

            int cnt = memberService.signUpMember(dto);
            if (cnt != 1) {
                msg = "회원 등록 실패: 등록 중 오류가 발생했습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(dto);
                break label;
            }

            msg = "회원 등록 성공";
            logger.info("{}: {}", msg, cnt);
            res.setStatus(201);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "회원 등록 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(500);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "updateMemberById", notes = "회원 아이디를 입력 받아 회원 수정 처리")
    @PutMapping("/{memberId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 수정 성공", response = UpdateMemberByIdResponseDto.class),
            @ApiResponse(code = 400, message = "회원 정보 수정 실패", response = UpdateMemberByIdResponseDto.class),
            @ApiResponse(code = 401, message = "회원 정보 수정 실패", response = UpdateMemberByIdResponseDto.class)
    })
    public ResponseEntity<UpdateMemberByIdResponseDto> updateMemberById(
            @PathVariable("memberId") int memberId,
            @RequestBody UpdateMemberByIdRequestDto dto,
            HttpServletRequest request
    ) {
        UpdateMemberByIdResponseDto res = new UpdateMemberByIdResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "회원 정보 수정 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "회원 정보 수정 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            // 로그인 한 유저가 자신과 관련된 요청을 하는지 확인
            int tokenMemberId = jwtUtil.getMemberId(token);
            if (memberId != tokenMemberId) {
                msg = "회원 정보 수정 실패: 허용되지 않은 접근입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                    put("tokenMemberId", tokenMemberId);
                }});
                break label;
            }

            // 존재하는 유저인지 확인
            MemberDto member = memberService.getMemberById(memberId);
            if (member == null) {
                msg = "회원 정보 수정 실패: 해당 회원 정보가 존재하지 않습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                }});
                break label;
            }

            logger.debug("회원 정보 수정: 기존 정보 {}", member);
            logger.debug("회원 정보 수정: 파라미터 {}", dto);

            member.setPassword(dto.getPassword() != null ? dto.getPassword() : null);
            member.setName(dto.getName() != null ? dto.getName() : null);
            member.setAge(dto.getAge() > 0 ? dto.getAge() : 0);
            member.setSex(dto.getSex() != null ? dto.getSex() : null);
            if (dto.getAddress() != null) {
                // TODO: 주소 바뀌면 동 코드도 업데이트 가능하도록.
                member.setAddress(dto.getAddress());
            } else {
                member.setAddress(null);
            }

            logger.debug("회원 정보 수정: 수정될 정보 {}", member);

            int cnt = memberService.updateMember(member);
            if (cnt != 1) {
                msg = "회원 정보 수정 실패: 수정 중 오류가 발생했습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                break label;
            }

            msg = "회원 정보 수정 성공";
            logger.info("{}: {}", msg, cnt);
            res.setStatus(200);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "회원 정보 수정 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(400);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "회원 삭제", notes = "회원 아이디를 받아 회원 삭제 처리")
    @DeleteMapping("/{memberId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 삭제 성공", response = DeleteMemberByIdResponseDto.class),
            @ApiResponse(code = 400, message = "회원 삭제 실패", response = DeleteMemberByIdResponseDto.class)
    })
    public ResponseEntity<DeleteMemberByIdResponseDto> deleteMemberById(
            @PathVariable("memberId") int memberId,
            HttpServletRequest request
    ) {
        DeleteMemberByIdResponseDto res = new DeleteMemberByIdResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "회원 삭제 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "회원 삭제 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            // 로그인 한 유저가 자신과 관련된 요청을 하는지 확인
            int tokenMemberId = jwtUtil.getMemberId(token);
            if (memberId != tokenMemberId) {
                msg = "회원 삭제 실패: 허용되지 않은 접근입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                    put("tokenMemberId", tokenMemberId);
                }});
                break label;
            }

            // 존재하는 유저인지 확인
            MemberDto member = memberService.getMemberById(memberId);
            if (member == null) {
                msg = "회원 삭제 실패: 해당 회원 정보가 존재하지 않습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("memberId", memberId);
                }});
                break label;
            }

            int cnt = memberService.deleteMemberById(memberId);
            if (cnt != 1) {
                msg = "회원 삭제 실패: 삭제 중 오류가 발생했습니다.";
                logger.info(msg);
                res.setStatus(400);
                res.setMessage(msg);
                break label;
            }

            msg = "회원 삭제 성공";
            logger.debug("{}: {}", msg, cnt);
            res.setStatus(200);
            res.setMessage(msg);
        } catch (Exception e) {
            msg = "회원 삭제 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(400);
            res.setMessage(msg);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "전체 회원 정보 조회", notes = "전체 회원 정보를 조회")
    @GetMapping("/")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 목록 조회 성공", response = GetMembersResponseDto.class),
            @ApiResponse(code = 400, message = "회원 목록 조회 실패", response = GetMembersResponseDto.class)
    })
    public ResponseEntity<GetMembersResponseDto> getMembers(
            HttpServletRequest request
    ) {
        GetMembersResponseDto res = new GetMembersResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "회원 목록 조회 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "회원 목록 조회 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            List<MemberDto> list = memberService.getMembers();
            logger.debug("회원 목록 조회: {}", list.size());

            msg = "회원 목록 조회 성공";
            logger.info("{}: {}", msg, list.size());
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(list);
        } catch (Exception e) {
            msg = "회원 목록 조회 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(400);
            res.setMessage(msg);
            res.setData(null);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @ApiOperation(value = "회원 정보 검색", notes = "회원 아이디를 받아 회원 정보 검색")
    @GetMapping("/{memberId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 검색 성공", response = GetMemberByIdResponseDto.class),
            @ApiResponse(code = 400, message = "회원 정보 검색 실패", response = GetMemberByIdResponseDto.class)
    })
    public ResponseEntity<GetMemberByIdResponseDto> getMemberById(
            @PathVariable("memberId") int memberId,
            HttpServletRequest request
    ) {
        GetMemberByIdResponseDto res = new GetMemberByIdResponseDto();
        String msg;

        label:
        try {
            // 로그인 한 상태인지 확인
            String token = request.getHeader("Authorization");
            if (token == null) {
                msg = "회원 정보 검색 실패: 로그인이 필요한 서비스입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                break label;
            }
            if (!jwtUtil.checkToken(token)) {
                msg = "회원 정보 검색 실패: 사용 불가능한 토큰입니다.";
                logger.info(msg);
                res.setStatus(401);
                res.setMessage(msg);
                res.setData(new HashMap<String, Object>() {{
                    put("Authorization", token);
                }});
                break label;
            }

            MemberDto member = memberService.getMemberById(memberId);
            logger.debug("회원 정보 검색: {}", member);

            msg = "회원 정보 검색 성공";
            logger.info("{}: {}", msg, member);
            res.setStatus(200);
            res.setMessage(msg);
            res.setData(member);
        } catch (Exception e) {
            msg = "회원 정보 검색 실패";
            logger.error("{}: {}", msg, e.getMessage());
            res.setStatus(400);
            res.setMessage(msg);
            res.setData(null);
        }

        return ResponseEntity.status(res.getStatus()).body(res);
    }

}
