package com.ssafy.whereismyhome.member.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.whereismyhome.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	
	MemberDto loginMember(Map<String, String> map) throws SQLException;
	void signUpMember(MemberDto memberDto) throws SQLException;
	void updateMember(MemberDto memberDto) throws SQLException;
	void deleteMember(String user_id) throws SQLException;
	MemberDto getMember(String user_id) throws SQLException;
	
}
