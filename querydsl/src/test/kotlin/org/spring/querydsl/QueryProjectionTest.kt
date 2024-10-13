package org.spring.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.dto.QMemberDto
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class QueryProjectionTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    /**
     * DTO가 querydsl에 의존하게 되는 단점이 있다.
     */
    @Test
    fun findDtoByProjection() {
        val result =
            queryFactory
                .select(QMemberDto(member.id, member.username))
                .from(member)
                .fetch()
        for (memberDto in result) {
            println("memberDto = $memberDto")
        }
    }
}
