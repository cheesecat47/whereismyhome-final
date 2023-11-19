package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpMemberRequestDto;

import java.sql.SQLException;
import java.util.List;


public interface MemberService {

    MemberDto loginMember(String memberId, String password) throws SQLException;

    int signUpMember(SignUpMemberRequestDto memberDto) throws SQLException;

    int updateMember(MemberDto memberDto) throws SQLException;

    int deleteMemberById(int memberId) throws SQLException;

    List<MemberDto> getMembers() throws SQLException;

    MemberDto getMemberById(int memberId) throws SQLException;
}
