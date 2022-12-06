package devcourse.baemin.domain.member.service;

import devcourse.baemin.domain.member.model.Member;
import devcourse.baemin.domain.member.model.MemberDto;
import devcourse.baemin.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(MemberDto.JoinRequestDto joinRequestDto) {
        this.memberRepository.save(joinRequestDto.toEntity());
    }

    public Optional<Member> login(String memberId, String password) {
        return memberRepository.findById(memberId)
                .filter(member -> member.getPassword().equals(password))
                .stream().findFirst();
    }

    public MemberDto.ResponseDto findById(String memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("No Member exists for memberId={0}", memberId)
                ));

        return new MemberDto.ResponseDto(foundMember);
    }
}
