package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.repository.MemberRepository;
import study.querydsl.repository.MemberSupportedRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    private final MemberSupportedRepository memberSupportedRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCond cond){
        return memberRepository.findDynamicDslWhere(cond);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCond cond, Pageable pageable){
        return memberRepository.findPageSimple(cond, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCond cond, Pageable pageable){
        return memberRepository.findPageComplex(cond, pageable);
    }

    @GetMapping("/v4/members")
    public Page<QMemberTeamDto> searchMemberV4(MemberSearchCond cond, Pageable pageable){
        return memberSupportedRepository.applyPagination(cond, pageable);
    }
    @GetMapping("/v5/members")
    public Page<QMemberTeamDto> searchMemberV5(MemberSearchCond cond, Pageable pageable){
        return memberSupportedRepository.applyPagination2(cond, pageable);
    }
}
