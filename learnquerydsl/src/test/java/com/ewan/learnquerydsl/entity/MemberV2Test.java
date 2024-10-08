package com.ewan.learnquerydsl.entity;

import com.querydsl.core.Tuple;
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
        @Test
        public void join_on_filtering(){

                Team teamA = new Team("teamA");
                Team teamB = new Team("teamB");

                Member member1 = new Member("member1", 10, teamA);
                Member member2 = new Member("member2", 20, teamB);

                em.persist(teamA);
                em.persist(teamB);

                em.persist(member1);
                em.persist(member2);



                List<Tuple> list = query
                        .select(member, team)
                        .from(member)
                        .leftJoin(member.team, team)
                        .on(team.name.eq("teamA"))
                        .fetch();
                list
                        .forEach(System.out::println);
        }
        @Test
        public void join_on_no_relation(){
                Team teamA = new Team("teamA");
                Team teamB = new Team("teamB");

                Member member1 = new Member("member1", 10, teamA);
                Member member2 = new Member("member2", 20, teamB);
                Member member3 = new Member("teamB", 20, teamB);
                Member member4 = new Member("teamB", 20, teamB);



                em.persist(teamA);
                em.persist(teamB);

                em.persist(member1);
                em.persist(member2);
                em.persist(member3);
                em.persist(member4);

                List<Tuple> list = query
                        .select(member, team)
                        .from(member)
                        .leftJoin(team)
                        .on(member.username.eq(team.name))
                        .fetch();
                list
                        .forEach(System.out::println);
        }

}