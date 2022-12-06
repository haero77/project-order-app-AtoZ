package devcourse.baemin.domain.member.repository;

import devcourse.baemin.domain.member.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    List<Member> findAll();

    Optional<Member> findById(String memberId);
}
