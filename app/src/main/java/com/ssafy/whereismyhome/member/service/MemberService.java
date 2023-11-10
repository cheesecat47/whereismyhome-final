package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.model.MemberDto;

import java.sql.SQLException;


public interface MemberService {

    MemberDto loginMember(String user_id, String password) throws SQLException;

    void signUpMember(MemberDto memberDto) throws SQLException;

    void updateMember(MemberDto memberDto) throws SQLException;

    void deleteMember(String user_id) throws SQLException;

    MemberDto getMember(String user_id) throws SQLException;
}
