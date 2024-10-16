package org.spring.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.QMember.member
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class BulkTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }
    @Test
    fun bulk() {
        val count = queryFactory
            .update(member)
            .set(member.username, "비회원")
            .where(member.id.lt(28))
            .execute()
        em.flush()
        em.clear()
        // 벌크 연산 후 영속성 컨텍스트 초기화
        // 벌크 연산은 영속성 컨텍스트를 무시하고 DB에 직접 쿼리를 날린다.
        // 따라서 영속성 컨텍스트에 있는 엔티티와 DB에 있는 엔티티의 상태가 다를 수 있다.
        val members = queryFactory
            .selectFrom(member)
            .fetch()
    }
}