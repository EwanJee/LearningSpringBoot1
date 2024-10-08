@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.spring.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.Member
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@SpringBootTest
class QuerydslApplicationTests {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup()  {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun insetTest() {
        // given
        val member = Member("member1")
        em.persist(member)

        // when
        val findMember = em.find(Member::class.java, member.id)

        // then
        assert(findMember == member)
    }

    @Test
    fun startQuerydsl() {
        // given
        val member1 = Member("member1")
        em.persist(member1)

        // when
        val findMember =
            queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne()

        // then
        assert(findMember == member1)
    }
}
