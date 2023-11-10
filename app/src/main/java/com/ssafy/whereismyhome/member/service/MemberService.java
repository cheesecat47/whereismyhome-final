package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpRequestDto;

import java.sql.SQLException;
import java.util.List;


public interface MemberService {

    MemberDto loginMember(String user_id, String password) throws SQLException;

    int signUpMember(SignUpRequestDto memberDto) throws SQLException;

    void updateMember(MemberDto memberDto) throws SQLException;

    void deleteMember(String user_id) throws SQLException;

    List<MemberDto> getMembers() throws SQLException;

    MemberDto getMemberByUserId(String user_id) throws SQLException;
}
