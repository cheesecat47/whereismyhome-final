package com.ssafy.whereismyhome.member.controller;

import com.ssafy.whereismyhome.member.model.*;
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
@Api(tags = {"멤버 컨트롤러  API V1"})
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 입력 받아 로그인 처리")
    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "로그인 실패", response = LoginResponseDto.class),
            @ApiResponse(code = 500, message = "로그인 실패", response = LoginResponseDto.class)
    })
    public ResponseEntity<LoginResponseDto> loginMember(@RequestBody LoginRequestDto dto) {
        LoginResponseDto res = new LoginResponseDto();

        try {
            if (dto.getUser_id().equals("") || dto.getPassword().equals("")) {
                res.setStatus(401);
                res.setMessage("로그인 실패: 아이디, 비밀번호는 필수입니다.");
                res.setData(dto);
            } else {
                MemberDto member = memberService.loginMember(dto.getUser_id(), dto.getPassword());
                if (member == null) {
                    res.setStatus(401);
                    res.setMessage("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
                    res.setData(dto);
                } else {
                    logger.debug("로그인 완료: {}", member);
                    res.setStatus(200);
                    res.setMessage("로그인 성공");
                    res.setData(member);
                }
            }
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("로그인 실패: " + e.getMessage());
            res.setData(null);
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원 등록", notes = "회원 정보를 입력 받아 회원 가입 처리")
    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 등록 성공", response = SignUpMemberResponseDto.class),
            @ApiResponse(code = 400, message = "회원 등록 실패", response = SignUpMemberResponseDto.class)
    })
    public ResponseEntity<SignUpMemberResponseDto> signUpMember(@RequestBody SignUpMemberRequestDto dto) {
        SignUpMemberResponseDto res = new SignUpMemberResponseDto();

        try {
            int cnt = memberService.signUpMember(dto);
            assert cnt == 1;
            logger.debug("회원 등록 완료: {}", cnt);

            res.setStatus(201);
            res.setMessage("회원 등록 성공");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("회원 등록 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원 수정", notes = "회원 아이디를 입력 받아 회원 수정 처리")
    @PostMapping("/{userid}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 수정 성공", response = UpdateMemberResponseDto.class),
            @ApiResponse(code = 400, message = "회원 수정 실패", response = UpdateMemberResponseDto.class)
    })
    public ResponseEntity<UpdateMemberResponseDto> updateMember(@PathVariable("userid") String user_id, @RequestBody UpdateMemberRequestDto dto) {
        UpdateMemberResponseDto res = new UpdateMemberResponseDto();

        try {
            MemberDto member = memberService.getMemberByUserId(user_id);
            assert member != null : "해당 회원 정보가 존재하지 않습니다.";
            logger.debug("회원 수정: 기존 정보 {}", member);
            logger.debug("회원 수정: 파라미터 {}", dto);

            member.setAge(dto.getAge() > 0 ? dto.getAge() : member.getAge());
            member.setEmail_account(dto.getEmail_account() != null ? dto.getEmail_account() : member.getEmail_account());
            member.setEmail_domain(dto.getEmail_domain() != null ? dto.getEmail_domain() : member.getEmail_domain());
            member.setPassword(dto.getPassword() != null ? dto.getPassword() : member.getPassword());
            member.setSex(dto.getSex() != null ? dto.getSex() : member.getSex());

            logger.debug("회원 수정: 수정될 정보 {}", member);

            int cnt = memberService.updateMember(member);
            assert cnt == 1;
            logger.debug("회원 수정 완료: {}", cnt);

            res.setStatus(200);
            res.setMessage("회원 수정 성공");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("회원 수정 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원 삭제", notes = "회원 아이디를 받아 회원 삭제 처리")
    @DeleteMapping("/{userid}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 삭제 성공", response = DeleteMemberByUserIdResponseDto.class),
            @ApiResponse(code = 400, message = "회원 삭제 실패", response = DeleteMemberByUserIdResponseDto.class)
    })
    public ResponseEntity<DeleteMemberByUserIdResponseDto> deleteMemberByUserId(@PathVariable("userid") String user_id) {
        DeleteMemberByUserIdResponseDto res = new DeleteMemberByUserIdResponseDto();

        try {
            int cnt = memberService.deleteMemberByUserId(user_id);
            assert cnt == 1;
            logger.debug("회원 삭제 완료: {}", cnt);

            res.setStatus(200);
            res.setMessage("회원 삭제 성공");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("회원 삭제 실패");
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "전체 회원 정보 조회", notes = "전체 회원 정보를 조회")
    @GetMapping("/")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 목록 조회 성공", response = GetMembersResponseDto.class),
            @ApiResponse(code = 400, message = "회원 목록 조회 실패", response = GetMembersResponseDto.class)
    })
    public ResponseEntity<GetMembersResponseDto> getMembers() {
        GetMembersResponseDto res = new GetMembersResponseDto();

        try {
            List<MemberDto> list = memberService.getMembers();
            logger.debug("회원 목록 조회: {}", list.size());

            res.setStatus(200);
            res.setMessage("회원 목록 조회 성공");
            res.setData(list);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("회원 목록 조회 실패");
            res.setData(null);
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

    @ApiOperation(value = "회원 정보 검색", notes = "회원 아이디를 받아 회원 정보 검색")
    @GetMapping("/{userid}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 정보 검색 성공", response = GetMemberByUserIdResponseDto.class),
            @ApiResponse(code = 400, message = "회원 정보 검색 실패", response = GetMemberByUserIdResponseDto.class)
    })
    public ResponseEntity<GetMemberByUserIdResponseDto> getMemberByUserId(@PathVariable("userid") String user_id) {
        GetMemberByUserIdResponseDto res = new GetMemberByUserIdResponseDto();

        try {
            MemberDto member = memberService.getMemberByUserId(user_id);
            logger.debug("회원 정보 검색: {}", member);

            res.setStatus(200);
            res.setMessage("회원 정보 검색 성공");
            res.setData(member);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            res.setStatus(400);
            res.setMessage("회원 정보 검색 실패");
            res.setData(null);
        }

        return ResponseEntity
                .status(res.getStatus())
                .body(res);
    }

}
