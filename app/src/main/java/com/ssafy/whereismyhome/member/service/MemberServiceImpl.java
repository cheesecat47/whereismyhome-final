package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.mapper.MemberMapper;
import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpRequestDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberDto loginMember(String user_id, String password) throws SQLException {
        return memberMapper.loginMember(user_id, password);
    }

    @Override
    public int signUpMember(SignUpRequestDto memberDto) throws SQLException {
        return memberMapper.signUpMember(memberDto);
    }

    @Override
    public void updateMember(MemberDto memberDto) throws SQLException {
        memberMapper.updateMember(memberDto);
    }

    @Override
    public void deleteMember(String user_id) throws SQLException {
        memberMapper.deleteMember(user_id);
    }

    @Override
    public MemberDto getMember(String user_id) throws SQLException {
        return memberMapper.getMember(user_id);
    }

}
