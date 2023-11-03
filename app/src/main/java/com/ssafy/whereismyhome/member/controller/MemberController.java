package com.ssafy.whereismyhome.member.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = {"유저 컨트롤러  API V1"})
@Slf4j
public class MemberController {
	
	private MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@ApiOperation(value = "로그인" , notes = "아이디와 비밀번호를 입력 받아 로그인 처리")
	@PostMapping("/login")
	public void loginMember(Map<String, String> map) {
		try {
			MemberDto dto = memberService.loginMember(map);
			System.out.println("로그인 완료");
			System.out.println(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "회원등록", notes = "회원 정보를 입력 받아 회원 가입 처리")
	@PostMapping("/user")
	public void SignUpMember(MemberDto dto) {
		try {
			memberService.signUpMember(dto);
			System.out.println("회원 등록 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "회원수정", notes = "회원 아이디를 입력 받아 회원 수정 처리")
	@PostMapping("/user/{userid}")
	public void UpdateMember(MemberDto dto) {
		try {
			memberService.updateMember(dto);
			System.out.println("회원 수정 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "회원삭제", notes = "회원 아이디를 받아 회원 삭제 처리")
	@DeleteMapping("/user/{userid}")
	public void deleteMember(@PathVariable("userid") String user_id) {
		try {
			memberService.deleteMember(user_id);
			System.out.println("회원 삭제 완료");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "회원 정보 검색", notes = "회원 아이디를 받아 정보 검색 처리")
	@GetMapping("/user/{userid}")
	public void getMember(@PathVariable("userid") String user_id) {
		try {
			MemberDto dto = memberService.getMember(user_id);
			System.out.println(dto);
			System.out.println("회원 정보 검색");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
