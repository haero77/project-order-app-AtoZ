package devcourse.baemin.domain.member;

public class Member {

    private final String memberId;
    private final String password;
    private final String memberName;

    public Member(String memberId, String password, String memberName) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberName() {
        return memberName;
    }
}
