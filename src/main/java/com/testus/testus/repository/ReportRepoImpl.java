package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.Report;
import com.testus.testus.domain.User;
import com.testus.testus.dto.review.ReportListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.testus.testus.domain.QExperienceRecruitment.experienceRecruitment;
import static com.testus.testus.domain.QReport.report;
import static com.testus.testus.domain.QReview.review;
import static com.testus.testus.domain.QUser.user;

public class ReportRepoImpl extends QuerydslRepositorySupport implements ReportRepoCustom {

    private final JPAQueryFactory queryFactory;
    public ReportRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(Report.class);
        this.queryFactory = jpaQueryFactory;
    }


    @Override
    public Page<ReportListDto> getUserCreateReportList(User targetUser, Pageable pageable) {
        List<ReportListDto> data = queryFactory.select(Projections.constructor(ReportListDto.class,
                        report.reportSeq,
                        report.answerStatus,
                        report.inquiryContents,
                        report.reportCategory,
                        report.createUser.userName,
                        report.createDate
                ))
                .from(report)
                .join(user).on(report.createUser.userSeq.eq(user.userSeq))
                .where(report.createUser.userSeq.eq(targetUser.getUserSeq()))
                .orderBy(report.reportSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(report.reportSeq.count())
                .from(report)
                .join(user).on(report.createUser.userSeq.eq(user.userSeq))
                .where(report.createUser.userSeq.eq(targetUser.getUserSeq()))
                ;

        return PageableExecutionUtils.getPage(data, pageable, count::fetchOne);
    }


    @Override
    public Page<ReportListDto> getReportByUserTest(User targetUser, Pageable pageable) {
        List<ReportListDto> data = queryFactory.select(Projections.constructor(ReportListDto.class,
                        report.reportSeq,
                        report.answerStatus,
                        report.inquiryContents,
                        report.reportCategory,
                        report.createUser.userName,
                        report.createDate
                ))
                .from(report)
                .join(user).on(report.createUser.userSeq.eq(user.userSeq))
                .join(experienceRecruitment).on(experienceRecruitment.seq.eq(report.experienceRecruitment.seq))
                .where(experienceRecruitment.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                report.experienceRecruitment.seq.eq(experienceRecruitment.seq)
                        )
                )
                .orderBy(report.reportSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(report.reportSeq.count())
                .from(report)
                .join(user).on(report.createUser.userSeq.eq(user.userSeq))
                .join(experienceRecruitment).on(experienceRecruitment.seq.eq(report.experienceRecruitment.seq))
                .where(experienceRecruitment.createUser.userSeq.eq(targetUser.getUserSeq()).and(
                                report.experienceRecruitment.seq.eq(experienceRecruitment.seq)
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
