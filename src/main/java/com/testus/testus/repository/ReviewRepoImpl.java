package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.Review;
import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReportListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;


import static com.testus.testus.domain.QExperienceRecruitment.experienceRecruitment;
import static com.testus.testus.domain.QReport.report;
import static com.testus.testus.domain.QReview.review;
import static com.testus.testus.domain.QUser.user;

public class ReviewRepoImpl extends QuerydslRepositorySupport implements ReviewRepoCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.queryFactory = jpaQueryFactory;
    }




}
