package study.querydsl.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.repository.support.Querydsl4RepositorySupport;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@Repository
public class MemberSupportedRepository extends Querydsl4RepositorySupport {

    public MemberSupportedRepository() {
        super(Member.class);
    }

    public List<Member> basicSelect(){
        return select(member)
                .from(member)
                .fetch();
    }

    public List<Member> basicSelectFrom(){
        return selectFrom(member)
                .fetch();
    }


    //기본 패아장 처리
    public Page<Member> searchPageByApplypage(MemberSearchCond cond, Pageable pageable){
        JPAQuery<Member> query = selectFrom(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(cond.getUsername()),
                        teamNameEq(cond.getTeamName()),
                        ageGoe(cond.getAgeGoe()),
                        ageLoe(cond.getAgeLoe())
                );

        List<Member> content = getQuerydsl().applyPagination(pageable, query).fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    //Querydsl4RepositorySupport>contentQuery만 호출
    public Page<QMemberTeamDto> applyPagination(MemberSearchCond cond, Pageable pageable){
        return applyPagination(
                pageable,
                contentQuery->contentQuery.select(new QMemberTeamDto(
                                                                member.id,
                                                                member.username,
                                                                member.age,
                                                                team.id,
                                                                team.name ))
                                                            .from(member)
                                                            .leftJoin(member.team, team)
                                                            .where(
                                                                    usernameEq(cond.getUsername()),
                                                                    teamNameEq(cond.getTeamName()),
                                                                    ageGoe(cond.getAgeGoe()),
                                                                    ageLoe(cond.getAgeLoe())));
    }
    //Querydsl4RepositorySupport>contentQuery & countQuery 호출
    public Page<QMemberTeamDto> applyPagination2(MemberSearchCond cond, Pageable pageable){
        return applyPagination(
                pageable,
                contentQuery-> contentQuery.select(new QMemberTeamDto(
                            member.id,
                            member.username,
                            member.age,
                            team.id,
                            team.name ))
                        .from(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(cond.getUsername()),
                                teamNameEq(cond.getTeamName()),
                                ageGoe(cond.getAgeGoe()),
                                ageLoe(cond.getAgeLoe())
                        ),
                countQuery->countQuery.selectFrom(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(cond.getUsername()),
                                teamNameEq(cond.getTeamName()),
                                ageGoe(cond.getAgeGoe()),
                                ageLoe(cond.getAgeLoe())
                        )
        );
    }

    private BooleanExpression usernameEq(String username) {  return hasText(username) ? member.username.eq(username) : null;  }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

}
