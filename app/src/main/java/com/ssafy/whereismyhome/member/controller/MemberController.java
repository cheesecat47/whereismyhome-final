package com.ssafy.whereismyhome.member.controller;

import com.ssafy.whereismyhome.member.model.*;
import com.ssafy.whereismyhome.member.service.MemberService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@Api(tags = {"유저 컨트롤러  API V1"})
@Slf4j
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 입력 받아 로그인 처리")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", required = true, defaultValue = ""),
            @ApiImplicitParam(name = "password", required = true, defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공", response = LoginResponseDto.class),
            @ApiResponse(code = 400, message = "로그인 실패", response = LoginResponseDto.class)
    })
    public ResponseEntity<LoginResponseDto> loginMember(String user_id, String password) {
        LoginResponseDto res = new LoginResponseDto();

        try {
            if (user_id.equals("") || password.equals("")) {
                throw new Exception("아이디, 비밀번호는 필수입니다.");
            }

            MemberDto member = memberService.loginMember(user_id, password);
            logger.debug("로그인 완료: {}", member);

            res.setStatus(HttpStatus.OK);
            res.setMessage("로그인 성공");
            res.setData(member);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setMessage("로그인 실패");
            res.setData(null);
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원등록", notes = "회원 정보를 입력 받아 회원 가입 처리")
    @PostMapping("/user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 등록 성공", response = SignUpMemberResponseDto.class),
            @ApiResponse(code = 400, message = "회원 등록 실패", response = SignUpMemberResponseDto.class)
    })
    public ResponseEntity<SignUpMemberResponseDto> signUpMember(SignUpMemberRequestDto dto) {
        SignUpMemberResponseDto res = new SignUpMemberResponseDto();

        try {
            int cnt = memberService.signUpMember(dto);
            assert cnt == 1;
            logger.debug("회원 등록 완료: {}", cnt);

            res.setStatus(HttpStatus.CREATED);
            res.setMessage("회원 등록 성공");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setMessage("회원 등록 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원수정", notes = "회원 아이디를 입력 받아 회원 수정 처리")
    @PostMapping("/user/{userid}")
    public void updateMember(MemberDto dto) {
        try {
            memberService.updateMember(dto);
            logger.debug("회원 수정 완료");
        } catch (SQLException e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    @ApiOperation(value = "회원삭제", notes = "회원 아이디를 받아 회원 삭제 처리")
    @DeleteMapping("/user/{userid}")
    public void deleteMember(@PathVariable("userid") String user_id) {
        try {
            memberService.deleteMember(user_id);
            logger.debug("회원 삭제 완료");
        } catch (SQLException e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    @ApiOperation(value = "전체 회원 정보 조회", notes = "전체 회원 정보를 조회")
    @GetMapping("/user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 목록 조회 성공", response = GetMembersResponseDto.class),
            @ApiResponse(code = 400, message = "회원 목록 조회 실패", response = GetMembersResponseDto.class)
    })
    public ResponseEntity<GetMembersResponseDto> getMembers() {
        GetMembersResponseDto res = new GetMembersResponseDto();

        try {
            List<MemberDto> list = memberService.getMembers();
            logger.debug("회원 목록 조회: {}", list.size());

            res.setStatus(HttpStatus.OK);
            res.setMessage("회원 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setMessage("회원 목록 조회 실패");
            res.setData(null);
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원 정보 검색", notes = "회원 아이디를 받아 회원 정보 검색")
    @GetMapping("/user/{userid}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 검색 성공", response = GetMemberByUserIdResponseDto.class),
            @ApiResponse(code = 400, message = "회원 정보 검색 실패", response = GetMemberByUserIdResponseDto.class)
    })
    public ResponseEntity<GetMemberByUserIdResponseDto> getMemberByUserId(@PathVariable("userid") String user_id) {
        GetMemberByUserIdResponseDto res = new GetMemberByUserIdResponseDto();

        try {
            MemberDto member = memberService.getMemberByUserId(user_id);
            logger.debug("회원 정보 검색: {}", member);

            res.setStatus(HttpStatus.OK);
            res.setMessage("회원 정보 검색 성공");
            res.setData(member);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setMessage("회원 정보 검색 실패");
            res.setData(null);
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

}
