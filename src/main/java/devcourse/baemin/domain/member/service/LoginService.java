package devcourse.baemin.domain.member.service;

import devcourse.baemin.domain.member.model.Member;
import devcourse.baemin.domain.member.model.MemberDto;
import devcourse.baemin.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto.ResponseDto login(MemberDto.LoginRequestDto loginRequestDto) {
        Member matchedMember = memberRepository.findById(loginRequestDto.getMemberId())
                .filter(member -> member.getPassword().equals(loginRequestDto.getPassword()))
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                                MessageFormat.format(
                                        "Login Error: member ''{0}'' doesn't exist or password doesn't match",
                                        loginRequestDto.getMemberId()
                                )
                        )
                );

        return new MemberDto.ResponseDto(matchedMember);
    }
}

