package org.spring.querydsl

import com.querydsl.core.Tuple
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@Suppress("ktlint:standard:class-naming")
@SpringBootTest
@Transactional
class 상수 {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun constant() {
        // [member1, A], [member2, A]
        val result: List<Tuple> =
            queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch()
        // {username}_{id}
        queryFactory
            .select(member.username.concat("_").concat(member.id.stringValue()))
            .from(member)
            .fetch()
    }
}
