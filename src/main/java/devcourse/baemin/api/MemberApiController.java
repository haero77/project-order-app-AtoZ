package devcourse.baemin.api;

import devcourse.baemin.domain.member.MemberDto;
import devcourse.baemin.domain.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberDto.ResponseDto> join(@RequestBody MemberDto.JoinRequestDto joinRequestDto) {
        MemberDto.ResponseDto memberResponseDto = memberService.join(joinRequestDto);

        return ResponseEntity.ok()
                .body(memberResponseDto);
    }

    @PostMapping("/members/member")
    public ResponseEntity<MemberDto.ResponseDto> login(
            @RequestBody MemberDto.LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {
        MemberDto.ResponseDto memberResponseDto = memberService.login(loginRequestDto);

        Cookie idCookie = new Cookie("memberId", memberResponseDto.getMemberId());
        response.addCookie(idCookie);

        return ResponseEntity.ok()
                .body(memberResponseDto);
    }
}
