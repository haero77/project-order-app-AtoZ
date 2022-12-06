package devcourse.baemin.domain.member;

import devcourse.baemin.domain.member.model.Member;
import devcourse.baemin.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원을 저장할 수 있다.")
    void testSave() {
        // given
        Member member = new Member("test", "test!", "testName");

        // when
        memberRepository.save(member);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).isNotEmpty();
    }

    @Test
    @DisplayName("회원 아이디로 회원 데이터를 조회할 수 있다.")
    void testMethodNameHere() {
        // given
        Member member = new Member("test", "test!", "testName");
        memberRepository.save(member);

        // when
        Optional<Member> foundMember = memberRepository.findById(member.getMemberId());

        // then
        String actualId = foundMember.get().getMemberId();
        String expectedId = member.getMemberId();
        assertThat(actualId).isEqualTo(expectedId);
    }
}