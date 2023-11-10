package com.ssafy.whereismyhome.member.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.whereismyhome.member.model.MemberDto;


public interface MemberService {
	
	MemberDto loginMember(Map<String, String> map) throws SQLException;
	void signUpMember(MemberDto memberDto) throws SQLException;
	void updateMember(MemberDto memberDto) throws SQLException;
	void deleteMember(String user_id) throws SQLException;
	MemberDto getMember(String user_id) throws SQLException;
}
