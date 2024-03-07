package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.Review;
import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReviewListDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.testus.testus.domain.QPost.post;
import static com.testus.testus.domain.QReview.review;
import static com.testus.testus.domain.QUser.user;

public class ReviewRepoImpl extends QuerydslRepositorySupport implements ReviewRepoCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<ReviewListDto> getReviewByUserTest(User targetUser) {
        return queryFactory.select(Projections.constructor(ReviewListDto.class,
                        review.reviewSeq,
                        review.answerYn,
                        review.contents,
                        review.reportReviewCategory,
                        review.createUser.userName,
                        review.createDate
                ))
                .from(review)
                .join(user).on(review.createUser.userSeq.eq(user.userSeq))
                .join(post).on(post.seq.eq(review.test.seq))
                .where(post.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                review.test.seq.eq(post.seq)
                        )
                )
                .orderBy(review.reviewSeq.desc())
                .limit(3)
                .fetch();

    }

    @Override
    public Long getNoAnswerReviewFromUserTest(User targetUser) {
        return queryFactory.select(
                        review.reviewSeq.count()
                )
                .from(review)
                .join(user).on(review.createUser.userSeq.eq(user.userSeq))
                .join(post).on(post.seq.eq(review.test.seq))
                .where(post.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                review.test.seq.eq(post.seq)
                        )
                ).fetchFirst();
    }
}
