package com.ssafy.whereismyhome.member.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.whereismyhome.member.mapper.MemberMapper;
import com.ssafy.whereismyhome.member.model.MemberDto;

@Service
public class MemberServiceImpl implements MemberService {

	private MemberMapper memberMapper;
	
	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}
	
	@Override
	public MemberDto loginMember(Map<String, String> map) throws SQLException {
		return memberMapper.loginMember(map);
	}

	@Override
	public void signUpMember(MemberDto memberDto) throws SQLException {
		memberMapper.signUpMember(memberDto);
		return;
	}

	@Override
	public void updateMember(MemberDto memberDto) throws SQLException {
		memberMapper.updateMember(memberDto);
		return;
	}

	@Override
	public void deleteMember(String user_id) throws SQLException {
		memberMapper.deleteMember(user_id);
		return;
	}

	@Override
	public MemberDto getMember(String user_id) throws SQLException {
		return memberMapper.getMember(user_id);
	}

}
