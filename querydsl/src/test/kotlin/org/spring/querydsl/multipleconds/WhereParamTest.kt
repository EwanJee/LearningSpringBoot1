package org.spring.querydsl.multipleconds

import com.querydsl.core.types.dsl.BooleanExpression
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
class WhereParamTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun whereParam() {
        val username = "member1"
        val id = 10L
        searchMember2(username, id)
    }

    private fun searchMember2(
        username: String,
        id: Long,
    ): MutableList<Member>? =
        queryFactory
            .selectFrom(member)
            .where(allEq(username, id))
            .fetch()

    private fun usernameEq(username: String?): BooleanExpression? = username?.let { member.username.eq(it) }

    private fun idEq(id: Long?): BooleanExpression? = id?.let { member.id.eq(it) }

    private fun allEq(
        username: String?,
        id: Long?,
    ): BooleanExpression? = usernameEq(username)?.and(idEq(id))
}
