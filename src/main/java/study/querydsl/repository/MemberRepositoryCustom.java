package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> findDynamicDslWhere(MemberSearchCond cond);
    Page<MemberTeamDto> findPageSimple(MemberSearchCond cond, Pageable pageable);
    Page<MemberTeamDto> findPageComplex(MemberSearchCond cond, Pageable pageable);
}