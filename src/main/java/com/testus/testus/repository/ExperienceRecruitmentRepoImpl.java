package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.dto.post.ExperienceRecruitmentThumbnailDto;
import com.testus.testus.enums.ExperienceRecruitmentCategory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

import static com.testus.testus.domain.QPost.post;


public class ExperienceRecruitmentRepoImpl extends QuerydslRepositorySupport implements ExperienceRecruitmentRepoCustom {

    private final JPAQueryFactory queryFactory;

    public ExperienceRecruitmentRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(ExperienceRecruitment.class);
        this.queryFactory = jpaQueryFactory;
    }


    @Override
    public List<ExperienceRecruitmentThumbnailDto> getLatestPost(String category) {
        return queryFactory.select(
                        Projections.constructor(ExperienceRecruitmentThumbnailDto.class,
                                post.seq,
                                post.title,
                                post.thumbnailUrl
                        ))
                .from(post)
                .where(post.endDate.after(LocalDate.now()),
                        eqCategory(category)
                )
                .limit(6)
                .orderBy(post.seq.desc())
                .fetch();

    }

    @Override
    public List<ExperienceRecruitmentThumbnailDto> getPopularPost(String category) {
        return queryFactory.select(
                        Projections.constructor(ExperienceRecruitmentThumbnailDto.class,
                                post.seq,
                                post.title,
                                post.thumbnailUrl
                        ))
                .from(post)
                .where(post.endDate.after(LocalDate.now()),
                        eqCategory(category)
                )
                .limit(6)
                .orderBy(post.star.desc())
                .fetch();
    }

    @Override
    public List<ExperienceRecruitment.MyPostDataResponse> getMyTest(User user) {
        return queryFactory.select(
                Projections.constructor(
                        ExperienceRecruitment.MyPostDataResponse.class,
                        post.seq,
                        post.thumbnailUrl,
                        post.title,
                        post.currentJoinCount,
                        post.endDate
                ))
                .from(post)
                .where(post.createUser.eq(user))
                .orderBy(post.seq.desc())
                .limit(3)
                .fetch();

    }


    private BooleanExpression eqCategory(String category) {
        return switch (category) {
            case "APP" -> post.category.eq(ExperienceRecruitmentCategory.APP);
            case "GAME" -> post.category.eq(ExperienceRecruitmentCategory.GAME);
            case "SERVICE" -> post.category.eq(ExperienceRecruitmentCategory.SERVICE);
            default -> throw new CustomException(Code.BAD_REQUEST);
        };
    }

}
