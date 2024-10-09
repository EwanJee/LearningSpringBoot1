package org.spring.querydsl

import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.spring.querydsl.entity.Member
import org.spring.querydsl.entity.QMember.member
import org.spring.querydsl.entity.QTeam.team
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class AggregationTest {
    @PersistenceContext
    lateinit var em: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setup() {
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun aggregation() {
        // given
        val member1 = Member("member1")
        val member2 = Member("member2")
        em.persist(member1)
        em.persist(member2)

        val result: MutableList<Tuple>? =
            queryFactory
                .select(member.username, member.id.count())
                .from(member)
                .groupBy(member.username)
                .fetch()
//        result?.get(0)?.get(member.username)
//        result?.get(0)?.get(member.count())
        // Tuple보다는 DTO로 조회하는 것을 권장
    }

    @Test
    fun group() {
        // given
        val member1 = Member("member1")
        val member2 = Member("member1")
        em.persist(member1)
        em.persist(member2)

        val result: MutableList<Tuple>? =
            queryFactory
                .select(team.name, member.id.count())
                .from(member)
                .join(team)
                .on(member.team.id.eq(team.id))
                .groupBy(member.username)
                .having(member.username.eq("member1"))
                .fetch()
    }
}
