package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.mapper.MemberMapper;
import com.ssafy.whereismyhome.member.model.MemberDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberDto loginMember(Map<String, String> map) throws SQLException {
        return memberMapper.loginMember(map);
    }

    @Override
    public void signUpMember(MemberDto memberDto) throws SQLException {
        memberMapper.signUpMember(memberDto);
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
