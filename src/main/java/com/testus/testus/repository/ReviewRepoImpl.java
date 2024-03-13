package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.OpenJPATemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.Review;
import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;


import static com.testus.testus.domain.QExperienceRecruitment.experienceRecruitment;
import static com.testus.testus.domain.QReview.review;
import static com.testus.testus.domain.QUser.user;

public class ReviewRepoImpl extends QuerydslRepositorySupport implements ReviewRepoCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ReviewListDto> getReviewByUserTest(User targetUser, Pageable pageable) {


        List<ReviewListDto> data = queryFactory.select(Projections.constructor(ReviewListDto.class,
                        review.reviewSeq,
                        review.answerYn,
                        review.contents,
                        review.reportReviewCategory,
                        review.createUser.userName,
                        review.createDate
                ))
                .from(review)
                .join(user).on(review.createUser.userSeq.eq(user.userSeq))
                .join(experienceRecruitment).on(experienceRecruitment.seq.eq(review.test.seq))
                .where(experienceRecruitment.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                review.test.seq.eq(experienceRecruitment.seq)
                        )
                )
                .orderBy(review.reviewSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(review.reviewSeq.count())
                .from(review)
                .join(user).on(review.createUser.userSeq.eq(user.userSeq))
                .join(experienceRecruitment).on(experienceRecruitment.seq.eq(review.test.seq))
                .where(experienceRecruitment.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                review.test.seq.eq(experienceRecruitment.seq)
                        )
                );

        return PageableExecutionUtils.getPage(data, pageable, count::fetchOne);

    }

    @Override
    public Long getNoAnswerReviewFromUserTest(User targetUser) {
        return queryFactory.select(
                        review.reviewSeq.count()
                )
                .from(review)
                .join(user).on(review.createUser.userSeq.eq(user.userSeq))
                .join(experienceRecruitment).on(experienceRecruitment.seq.eq(review.test.seq))
                .where(experienceRecruitment.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                review.test.seq.eq(experienceRecruitment.seq)
                        )
                ).fetchFirst();
    }
}
