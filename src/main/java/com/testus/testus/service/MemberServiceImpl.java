package com.testus.testus.service;

import com.testus.testus.common.oauth.userInfo.OAuth2UserInfo;
import com.testus.testus.common.oauth.userInfo.OAuth2UserInfoFactory;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.domain.Member;
import com.testus.testus.enums.SocialType;
import com.testus.testus.repository.MemberRepo;
import com.testus.testus.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepo memberRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Member oauthSignUp(String name, String nickname, String email, String subId, SocialType type) {
        Member member = Member.builder()
                .providerType(type.getTypeName())
                .userPassword("asdfasdf")
                .userName(nickname)
                .userEmail("test@gmail.com")
                .providerSubject(subId)
                .status('D')
                .marketingYn('N')
                .build();

        return memberRepo.save(member);
    }

    @Override
    public Optional<Member> findMember(String id) {
        return memberRepo.findByProviderSubject(id);
    }

    @Override
    @Transactional
    public ResponseDto<Member.MemberInfoDto> updateInfo(Member.MemberInfoUpdateDto memberInfoUpdateDto, Member member) {
        if (memberInfoUpdateDto.getPassword() == null ){
            memberRepo.updateInfo(memberInfoUpdateDto, member.getUserSeq(), null);
        } else {
            memberRepo.updateInfo(memberInfoUpdateDto, member.getUserSeq(), passwordEncoder.encode(memberInfoUpdateDto.getPassword()));
        }
        return new ResponseDto<>(Code.SUCCESS);
    }


    @Transactional
    public ResponseDto<Member.MemberInfoDto> oauthLogin(String provider, String code, String redirectUrl, HttpServletResponse response) {

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, code, redirectUrl);
        Optional<Member> findUser = memberRepo.findByProviderSubject(userInfo.getId());

        Member member;

        if (findUser.isPresent()) { // 기존 회원
            member = findUser.get();
            jwtTokenUtil.addJwtTokenInHeader(member, response);
        } else { // 신규 회원
            member = Member.builder()
                    .userName(userInfo.getName())
                    .userEmail(userInfo.getEmail())
                    .userPassword(null)
                    .providerType(provider)
                    .providerSubject(userInfo.getId())
                    .role("ROLE_USER")
                    .status('D')
                    .build();
            member = memberRepo.save(member);
        }
        return checkMemberStatus(member);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Member.MemberInfoDto> checkMemberStatus(Member member) {
        if (member.getStatus() == 'D'){ // 회원 정보 업데이트 필요
            return new ResponseDto<>(Code.REQUIRED_UPDATE_MEMBER_INFO, member.convertMemberInfoResponse());
        } else {
            return new ResponseDto<>(Code.SUCCESS, member.convertMemberInfoResponse());
        }
    }
}
