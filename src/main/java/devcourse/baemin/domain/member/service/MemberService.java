package devcourse.baemin.domain.member.service;

import devcourse.baemin.domain.member.model.Member;
import devcourse.baemin.domain.member.model.MemberDto;
import devcourse.baemin.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(MemberDto.JoinRequestDto joinRequestDto) {
        try {
            this.memberRepository.save(joinRequestDto.toEntity());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new IllegalArgumentException(
                    MessageFormat.format(
                            "Join Error: memberId ''{0}'' already in use.", joinRequestDto.getMemberId()
                    )
            );
        }
    }

    public MemberDto.ResponseDto login(MemberDto.LoginRequestDto loginRequestDto) {
        Member foundMember = memberRepository.findById(loginRequestDto.getMemberId())
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

        return new MemberDto.ResponseDto(foundMember);
    }

    public MemberDto.ResponseDto findById(String memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("No Member exists for memberId={0}", memberId)
                ));

        return new MemberDto.ResponseDto(foundMember);
    }
}
