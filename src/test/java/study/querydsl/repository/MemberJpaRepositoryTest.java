package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;


import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() throws Exception{
        Member member1 = new Member("member1",10);
        memberJpaRepository.save(member1);

        Member findMember = memberJpaRepository.findMember(member1.getId()).get();
        assertThat(member1).isEqualTo(findMember);

        List<Member> findMembers = memberJpaRepository.findAll();
        assertThat(findMembers).containsExactly(findMember);

        List<Member> findMembers2 = memberJpaRepository.findByUsername("member1");
        assertThat(findMembers2).extracting("username").containsExactly("member1");
    }

    @Test
    public void basicQuerydslTest() throws Exception{
        Member member1 = new Member("member1",10);
        memberJpaRepository.save(member1);

        List<Member> findMembers = memberJpaRepository.findAll_Querydsl();
        assertThat(findMembers).containsExactly(member1);

        List<Member> findMembers2 = memberJpaRepository.findByUsername_Querydsl("member1");
        assertThat(findMembers2).extracting("username").containsExactly("member1");
    }

    @Test
    public void dynamicQueryDslTest() throws Exception{
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCond cond = new MemberSearchCond();
        cond.setTeamName("teamB");
        cond.setAgeGoe(25);
        cond.setAgeLoe(40);

        List<MemberTeamDto> result = memberJpaRepository.findDynamicDsl(cond);

        assertThat(result).extracting("username").containsExactly("member3","member4");
    }

    @Test
    public void dynamicQueryDslTest2() throws Exception{
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCond cond = new MemberSearchCond();
        cond.setTeamName("teamB");
        cond.setAgeGoe(20);

        List<MemberTeamDto> result = memberJpaRepository.findDynamicDslWhere(cond);

        assertThat(result).extracting("username").containsExactly("member3","member4");
    }
}