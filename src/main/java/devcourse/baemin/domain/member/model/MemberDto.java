package devcourse.baemin.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinRequestDto {

        @NotEmpty
        private String memberId;
        @NotEmpty
        private String password;
        @NotEmpty
        private String memberName;

        public Member toEntity() {
            return new Member(memberId, password, memberName);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequestDto {

        @NotEmpty
        private String memberId;
        @NotEmpty
        private String password;
    }

    @Getter
    public static class ResponseDto {

        public String memberId;
        public String memberName;

        public ResponseDto(Member member) {
            this.memberId = member.getMemberId();
            this.memberName = member.getMemberName();
        }
    }
}