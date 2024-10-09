package org.spring.querydsl

import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.Member
import org.spring.querydsl.entity.QMember.member
import org.spring.querydsl.entity.QTeam.team
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class JoinTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun join() {
        val result =
            queryFactory
                .select(member)
                .from(member)
                .join(member.team, team)
                .where(member.username.eq("member1"))
                .fetch()
        Assertions
            .assertThat(result)
            .extracting("username")
            .containsExactly("member1")
    }

    @Test
    fun fetchJoin() {
        val result =
            queryFactory
                .select(member)
                .from(member)
                .join(member.team, team)
                .fetchJoin()
                .where(member.username.eq("member1"))
                .fetch()
        // fetchJoin은 연관된 엔티티를 한번에 조회
        // LAZY 로딩을 무시하고 즉시 로딩을 한다.
        Assertions
            .assertThat(result)
            .extracting("username")
            .containsExactly("member1")
    }

    @Test
    fun subQuery() {
        // given
        val member1 = Member("member1")
        em.persist(member1)

        val fetch =
            queryFactory
                .selectFrom(member)
                .where(
                    member.username.eq(
                        JPAExpressions
                            .select(member.username)
                            .from(member)
                            .where(member.username.eq("member1")),
                    ),
                ).fetch()

        val fetch1 =
            queryFactory
                .selectFrom(member)
                .where(
                    member.username.eq(
                        queryFactory
                            .select(member.username)
                            .from(member)
                            .where(member.username.eq("member1"))
                            .fetchOne(),
                    ),
                ).fetch()

        println(fetch)
        println(fetch1)
    }
}
