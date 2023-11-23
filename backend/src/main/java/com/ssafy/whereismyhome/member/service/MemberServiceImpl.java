package com.ssafy.whereismyhome.member.service;

import com.ssafy.whereismyhome.member.model.MemberDto;
import com.ssafy.whereismyhome.member.model.SignUpMemberRequestDto;
import com.ssafy.whereismyhome.member.model.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberDto loginMember(String emailAccount, String emailDomain, String password) throws SQLException {
        return memberMapper.loginMember(emailAccount, emailDomain, password);
    }

    @Override
    public int signUpMember(SignUpMemberRequestDto memberDto) throws SQLException {
        return memberMapper.signUpMember(memberDto);
    }

    @Override
    public int updateMember(MemberDto memberDto) throws SQLException {
        return memberMapper.updateMember(memberDto);
    }

    @Override
    public int deleteMemberById(int memberId) throws SQLException {
        return memberMapper.deleteMemberById(memberId);
    }

    @Override
    public List<MemberDto> getMembers() throws SQLException {
        return memberMapper.getMembers();
    }

    @Override
    public MemberDto getMemberById(int memberId) throws SQLException {
        return memberMapper.getMemberById(memberId);
    }

    @Override
    public int updateRefreshToken(String memberId, String refreshToken) throws SQLException {
        return memberMapper.updateRefreshToken(memberId, refreshToken);
    }

    @Override
    public boolean checkEmailDuplicate(String emailAccount, String emailDomain) throws SQLException {
        int cnt = memberMapper.checkEmailDuplicate(emailAccount, emailDomain);
        logger.debug("checkEmailDuplicate: cnt: {}", cnt);
        return cnt > 0;
    }

}
