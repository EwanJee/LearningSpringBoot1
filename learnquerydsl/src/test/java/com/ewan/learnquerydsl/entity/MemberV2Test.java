package com.ewan.learnquerydsl.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ewan.learnquerydsl.entity.QMember.member;
import static com.ewan.learnquerydsl.entity.QTeam.team;

@Transactional
@SpringBootTest
//@Transactional
class MemberV2Test {
        @Autowired
        EntityManager em;

        JPAQueryFactory query;

        @BeforeEach
        public void before(){
            query = new JPAQueryFactory(em);
        }

        @Test
        void test() {

                Team teamA = new Team("teamA");

                em.persist(teamA);

                em.persist(new Member("member1", 10, teamA));
                em.persist(new Member("member2", 20, teamA));

                List<Member> list = query
                        .selectFrom(member)
                        .join(member.team, team)
                        .where(member.username.eq("member1"))
                        .fetch();

                Assertions.assertThat(list)
                        .extracting("username")
                        .containsExactly("member1");
        }
}