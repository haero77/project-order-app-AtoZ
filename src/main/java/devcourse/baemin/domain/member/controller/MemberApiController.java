package devcourse.baemin.domain.member.controller;

import devcourse.baemin.domain.member.model.MemberDto;
import devcourse.baemin.domain.member.service.MemberService;
import devcourse.baemin.web.response.DefaultResponse;
import devcourse.baemin.web.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        memberService.join(joinRequestDto);
        MemberDto.ResponseDto memberResponseDto = memberService.findById(joinRequestDto.getMemberId());
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
