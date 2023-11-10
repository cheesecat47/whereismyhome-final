package com.ssafy.whereismyhome.member.mapper;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface MemberMapper {

    MemberDto loginMember(String user_id, String password) throws SQLException;

    int signUpMember(SignUpRequestDto memberDto) throws SQLException;

    void updateMember(MemberDto memberDto) throws SQLException;

    void deleteMember(String user_id) throws SQLException;

    List<MemberDto> getMembers() throws SQLException;

    MemberDto getMemberByUserId(String user_id) throws SQLException;

}
