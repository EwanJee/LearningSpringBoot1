package org.spring.querydsl.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberJpaRepository(
    val queryFactory: JPAQueryFactory
){

}