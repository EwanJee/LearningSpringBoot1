package org.spring.querydsl.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.spring.querydsl.dto.MemberDto
import org.spring.querydsl.entity.QMember.member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class MemberQuerydslRepository(
    val queryFactory: JPAQueryFactory,
) {
    fun paging1(
        memberDto: MemberDto,
        pageable: Pageable,
    ): Page<MemberDto> {
        val result: List<MemberDto> =
            queryFactory
                .select(
                    Projections.constructor(
                        MemberDto::class.java,
                        member.id,
                        member.username,
                    ),
                ).from(member)
                .orderBy(member.username.desc())
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()
        return PageableExecutionUtils.getPage(result, pageable) {
            queryFactory
                .select(
                    member.count(),
                ).from(member)
                .fetchOne()!!
        }
    }
}
