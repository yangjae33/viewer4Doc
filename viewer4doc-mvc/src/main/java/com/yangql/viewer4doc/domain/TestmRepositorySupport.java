package com.yangql.viewer4doc.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yangql.viewer4doc.domain.QTestm.testm;

@Repository
public class TestmRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public TestmRepositorySupport(JPAQueryFactory queryFactory) {
        super(Testm.class);
        this.queryFactory = queryFactory;
    }

    public List<Testm> findByName(String name) {
        return queryFactory
                .selectFrom(testm)
                .where(testm.name.eq(name))
                .fetch();
    }

}