package devcourse.baemin.domain.member.controller;

import devcourse.baemin.domain.member.model.MemberDto;
import devcourse.baemin.domain.member.service.LoginService;
import devcourse.baemin.domain.member.service.MemberService;
import devcourse.baemin.web.response.DefaultResponse;
import devcourse.baemin.web.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final LoginService loginService;

    public MemberApiController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberDto.ResponseDto> join(@RequestBody MemberDto.JoinRequestDto joinRequestDto) {
        memberService.join(joinRequestDto);
        MemberDto.ResponseDto memberResponseDto = memberService.findById(joinRequestDto.getMemberId());
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @PostMapping("/members/member")
    public DefaultResponse<?> login(
            @RequestBody MemberDto.LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {
        MemberDto.ResponseDto memberResponseDto = loginService.login(loginRequestDto);

        Cookie idCookie = new Cookie("memberId", memberResponseDto.getMemberId());
        response.addCookie(idCookie);

        return DefaultResponse.response(HttpStatus.OK.value(), ResponseMessage.LOGIN_SUCCESS, memberResponseDto);
    }
}
