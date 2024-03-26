package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.TestJoinMapping;
import com.testus.testus.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

import static com.testus.testus.domain.QExperienceRecruitment.experienceRecruitment;
import static com.testus.testus.domain.QTestJoinMapping.testJoinMapping;

public class TestJoinMapRepoImpl extends QuerydslRepositorySupport implements TestJoinMapRepoCustom {
    private final JPAQueryFactory queryFactory;

    public TestJoinMapRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(TestJoinMapping.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ExperienceRecruitment.MyPostDataResponse> getUserJoinTestList(User target, Pageable pageable) {
        JPQLQuery<ExperienceRecruitment.MyPostDataResponse> query = queryFactory.select(
                        Projections.constructor(
                                ExperienceRecruitment.MyPostDataResponse.class,
                                experienceRecruitment.seq,
                                experienceRecruitment.thumbnail,
                                experienceRecruitment.title,
                                experienceRecruitment.currentJoinCount,
                                experienceRecruitment.endDate
                        ))
                .from(testJoinMapping)
//                .join(user).on(testJoinMapping.user.userSeq.eq(target.getUserSeq()))
                .join(experienceRecruitment).on(testJoinMapping.experienceRecruitment.eq(experienceRecruitment))
                .where(testJoinMapping.user.userSeq.eq(target.getUserSeq()))
                .orderBy(experienceRecruitment.seq.desc());

        List<ExperienceRecruitment.MyPostDataResponse> testList =
                Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(testList, pageable, query.fetchCount());
    }
}
