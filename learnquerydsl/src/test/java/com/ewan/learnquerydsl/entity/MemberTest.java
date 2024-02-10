package com.ewan.learnquerydsl.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static com.ewan.learnquerydsl.entity.QMember.*;
@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    public EntityManager em;

    JPAQueryFactory query;

    @BeforeEach
    public void before(){
        query = new JPAQueryFactory(em);
    }

    @Test
    public void test(){
        em.persist(new Member("member1", 10));


        Member m1 = query
                .selectFrom(member)
                .fetchOne();
        Member m2 = query
                .selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();
        Assertions.assertThat(m1).isEqualTo(m2);

    }
    @Test
    public void resultFetch(){
        em.persist(new Member("member1", 10));
        em.persist(new Member("member2", 10));

        List<Member> fetch = query
                .selectFrom(member)
                .fetch();
        Member first = query
                .selectFrom(member)
                .fetchFirst();
        Member one = query
                .selectFrom(member)
                .fetchFirst();
        Assertions.assertThat(fetch.getFirst()).isEqualTo(first);

    }
    @Test
    public void sort(){
        //나이 내림 차순
        //회원 이름 올림차순
        //회원 이름 없으면 마지막에 출력
        em.persist(new Member("member1", 10));
        em.persist(new Member("member2", 20));
        em.persist(new Member("member3", 30));
        em.persist(new Member(null, 100));

        List<Member> result = query
                .selectFrom(member)
                .where(member.age.goe(10))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();
        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("member3");
        Assertions.assertThat(result.get(1).getUsername()).isEqualTo("member2");
        Assertions.assertThat(result.get(2).getUsername()).isEqualTo("member1");
        Assertions.assertThat(result.get(3).getUsername()).isNull();
    }
    @Test
    public void paging1(){
        em.persist(new Member("member1", 10));
        em.persist(new Member("member2", 20));
        em.persist(new Member("member3", 30));
        em.persist(new Member("member4", 40));
        em.persist(new Member("member5", 50));

        List<Member> result = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(3)
                .fetch();
        Assertions.assertThat(result.size()).isEqualTo(3);
    }
    @Test
    public void aggregation(){
        em.persist(new Member("member1", 10));
        em.persist(new Member("member2", 20));
        em.persist(new Member("member3", 30));
        em.persist(new Member("member4", 40));
        em.persist(new Member("member5", 50));

        List<Tuple> list = query
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();
        Tuple tuple = list.get(0);
        Assertions.assertThat(tuple.get(member.count())).isEqualTo(5);
        Assertions.assertThat(tuple.get(member.age.sum())).isEqualTo(150);
        Assertions.assertThat(tuple.get(member.age.avg())).isEqualTo(30);
        Assertions.assertThat(tuple.get(member.age.max())).isEqualTo(50);
        Assertions.assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }
    @Test
    public void group(){
        em.persist(new Member("member1", 10));
        em.persist(new Member("member2", 10));
        em.persist(new Member("member3", 30));
        em.persist(new Member("member4", 40));
        em.persist(new Member("member5", 50));

        List<Tuple> result = query
                .select(member.age, member.count())
                .from(member)
                .groupBy(member.age)
                .fetch();
        Tuple tuple = result.get(0);
        Tuple tuple1 = result.get(1);
        Tuple tuple2 = result.get(2);
        Tuple tuple3 = result.get(3);

        List<Tuple> result2 = query
                .select(member.age, member.count())
                .from(member)
                .groupBy(member.age)
                .having(member.age.gt(20))
                .fetch();

        Tuple tuple4 = result2.get(0);
        Tuple tuple5 = result2.get(1);
        Tuple tuple6 = result2.get(2);
        Assertions.assertThat(tuple4.get(member.age)).isEqualTo(30);
        Assertions.assertThat(tuple4.get(member.count())).isEqualTo(1);
        Assertions.assertThat(tuple5.get(member.age)).isEqualTo(40);
        Assertions.assertThat(tuple5.get(member.count())).isEqualTo(1);
        Assertions.assertThat(tuple6.get(member.age)).isEqualTo(50);
        Assertions.assertThat(tuple6.get(member.count())).isEqualTo(1);

        Assertions.assertThat(tuple.get(member.age)).isEqualTo(10);
        Assertions.assertThat(tuple.get(member.count())).isEqualTo(2);
        Assertions.assertThat(tuple1.get(member.age)).isEqualTo(30);
        Assertions.assertThat(tuple1.get(member.count())).isEqualTo(1);
        Assertions.assertThat(tuple2.get(member.age)).isEqualTo(40);
        Assertions.assertThat(tuple2.get(member.count())).isEqualTo(1);
        Assertions.assertThat(tuple3.get(member.age)).isEqualTo(50);
        Assertions.assertThat(tuple3.get(member.count())).isEqualTo(1);



    }

}