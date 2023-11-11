package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpMemberRequestDto;

import java.sql.SQLException;
import java.util.List;


public interface MemberService {

    MemberDto loginMember(String user_id, String password) throws SQLException;

    int signUpMember(SignUpMemberRequestDto memberDto) throws SQLException;

    int updateMember(MemberDto memberDto) throws SQLException;

    int deleteMemberByUserId(String user_id) throws SQLException;

    List<MemberDto> getMembers() throws SQLException;

    MemberDto getMemberByUserId(String user_id) throws SQLException;
}
