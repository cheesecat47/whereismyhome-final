package com.ssafy.whereismyhome.member.model.mapper;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpMemberRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface MemberMapper {

    MemberDto loginMember(String memberId, String password) throws SQLException;

    int signUpMember(SignUpMemberRequestDto memberDto) throws SQLException;

    int updateMember(MemberDto memberDto) throws SQLException;

    int deleteMemberById(String memberId) throws SQLException;

    List<MemberDto> getMembers() throws SQLException;

    MemberDto getMemberById(String memberId) throws SQLException;

}
