package org.spring.querydsl.multipleconds

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.Member
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class BooleanBuilderTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun booleanBuilder() {
        val username = "member1"
        val id = 10L
        searchMember(username, id)
    }

    /**
     * BooleanBuilder를 사용하면 동적 쿼리를 작성할 수 있다.
     */
    private fun searchMember(
        username: String,
        id: Long,
    ): MutableList<Member>? {
        val builder = BooleanBuilder()
        if (username.isNotEmpty()) {
            builder.and(member.username.eq(username))
        }
        builder.and(member.id.eq(id))
        return queryFactory
            .selectFrom(member)
            .where(builder)
            .fetch()
    }
}
