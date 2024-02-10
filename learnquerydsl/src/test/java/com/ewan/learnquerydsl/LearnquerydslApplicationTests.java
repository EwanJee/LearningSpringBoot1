package com.ewan.learnquerydsl;

import com.ewan.learnquerydsl.entity.Hello;
import com.ewan.learnquerydsl.entity.QHello;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class LearnquerydslApplicationTests {

    @Autowired
    EntityManager em;
    JPAQueryFactory query;

    @Test
    void contextLoads() {
        Hello hello = new Hello();
        QHello qHello = new QHello("h");

        Hello result = query
                .selectFrom(qHello)
                .fetchOne();

        Assertions.assertThat(result).isEqualTo(hello);
    }

}
