package devcourse.baemin.domain.member;

import devcourse.baemin.domain.member.Member;
import devcourse.baemin.domain.member.MemberDto;
import devcourse.baemin.domain.member.MemberRepository;
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

    public MemberDto.ResponseDto join(MemberDto.JoinRequestDto joinRequestDto) {
        try {
            this.memberRepository.save(joinRequestDto.toEntity());
            return findMemberById(joinRequestDto.getMemberId());
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

    public MemberDto.ResponseDto findMemberById(String memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("Can't find Member: No Member exists for memberId={0}", memberId)
                ));

        return new MemberDto.ResponseDto(foundMember);
    }
}
