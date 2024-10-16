package org.spring.querydsl.dto

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.Member
import org.spring.querydsl.entity.QMember.member
import org.spring.querydsl.entity.Team
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class DtoTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun findDtoByJPQL() {
        val team1 = Team("team1")
        val team2 = Team("team2")
        em.persist(team1)
        em.persist(team2)
        val member1 = Member("member1", team1)
        val member2 = Member("member2", team2)
        em.persist(member1)
        em.persist(member2)

        val result: List<MemberDto> =
            em
                .createQuery(
                    "select new org.spring.querydsl.dto.MemberDto(m.id, m.username) from Member m",
                    MemberDto::class.java,
                ).resultList
        for (memberDto in result) {
            println("memberDto = $memberDto")
        }
    }

    @Test
    fun findDtoBySetter() {
        val result: List<MemberDto> =
            queryFactory
                .select(
                    Projections.bean(
                        MemberDto::class.java,
                        member.id,
                        member.username,
                    ),
                ).from(member)
                .fetch()
        for (memberDto in result) {
            println("memberDto = $memberDto")
        }
    }

    @Test
    fun findDtoByField() {
        val result: List<MemberDto> =
            queryFactory
                .select(
                    Projections.fields(
                        MemberDto::class.java,
                        member.id.`as`("id"), // 만약 다른 dto 필드명이면 as로 매칭
                        member.username,
                    ),
                ).from(member)
                .fetch()
        for (memberDto in result) {
            println("memberDto = $memberDto")
        }
    }

    @Test
    fun findDtoByConstructor() {
        val result: List<MemberDto> =
            queryFactory
                .select(
                    Projections.constructor(
                        MemberDto::class.java,
                        member.id,
                        member.username,
                    ),
                ).from(member)
                .fetch()
        for (memberDto in result) {
            println("memberDto = $memberDto")
        }
    }
}
