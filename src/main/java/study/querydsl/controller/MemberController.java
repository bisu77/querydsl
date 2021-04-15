package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCond cond){
        return memberRepository.findDynamicDslWhere(cond);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV1(MemberSearchCond cond, Pageable pageable){
        return memberRepository.findPageSimple(cond, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCond cond, Pageable pageable){
        return memberRepository.findPageComplex(cond, pageable);
    }
}
