package org.spring.querydsl

import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@SpringBootTest
class CaseTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun case() {
        val result =
            queryFactory
                .select(
                    member.username
                        .`when`("member1")
                        .then("VIP")
                        .`when`("member2")
                        .then("BASIC")
                        .otherwise("BASIC"),
                ).from(member)
                .fetch()
        for (username in result) {
            println("username = $username")
        }
    }

    @Test
    fun complexCase() {
        queryFactory
            .select(
                CaseBuilder()
                    .`when`(member.username.eq("member1"))
                    .then("VIP")
                    .`when`(member.username.eq("member2"))
                    .then("BASIC")
                    .otherwise("BASIC"),
            ).from(member)
            .fetch()
        // 복잡한 case문은 CaseBuilder를 사용
        // CaseBuilder when 안에 in, eq 등 다양한 조건을 사용할 수 있다.
    }
}
