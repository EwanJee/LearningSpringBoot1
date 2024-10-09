package org.spring.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PagingTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun paging1() {
        val fetch =
            queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch()
    }

    @Test
    fun paging2() {
        val fetch =
            queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults() // Deprecated
    }
}
