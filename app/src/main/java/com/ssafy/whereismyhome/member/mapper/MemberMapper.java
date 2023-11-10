package com.ssafy.whereismyhome.member.mapper;

import com.ssafy.whereismyhome.member.model.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberMapper {

    MemberDto loginMember(String user_id, String password) throws SQLException;

    void signUpMember(MemberDto memberDto) throws SQLException;

    void updateMember(MemberDto memberDto) throws SQLException;

    void deleteMember(String user_id) throws SQLException;

    MemberDto getMember(String user_id) throws SQLException;

}
