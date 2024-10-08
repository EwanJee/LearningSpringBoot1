package org.spring.querydsl.configuration

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig(
    var em: EntityManager? = null,
) {
    @Bean
    fun queryFactory(): JPAQueryFactory = JPAQueryFactory(em)
}
