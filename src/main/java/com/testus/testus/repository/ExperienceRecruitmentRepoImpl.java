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

import static com.testus.testus.domain.QExperienceRecruitment.experienceRecruitment;


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
                                experienceRecruitment.seq,
                                experienceRecruitment.title,
                                experienceRecruitment.thumbnailUrl
                        ))
                .from(experienceRecruitment)
                .where(experienceRecruitment.endDate.after(LocalDate.now()),
                        eqCategory(category)
                )
                .limit(6)
                .orderBy(experienceRecruitment.seq.desc())
                .fetch();

    }

    @Override
    public List<ExperienceRecruitmentThumbnailDto> getPopularPost(String category) {
        return queryFactory.select(
                        Projections.constructor(ExperienceRecruitmentThumbnailDto.class,
                                experienceRecruitment.seq,
                                experienceRecruitment.title,
                                experienceRecruitment.thumbnailUrl
                        ))
                .from(experienceRecruitment)
                .where(experienceRecruitment.endDate.after(LocalDate.now()),
                        eqCategory(category)
                )
                .limit(6)
                .orderBy(experienceRecruitment.star.desc())
                .fetch();
    }

    @Override
    public List<ExperienceRecruitment.MyPostDataResponse> getMyTest(User user) {
        return queryFactory.select(
                Projections.constructor(
                        ExperienceRecruitment.MyPostDataResponse.class,
                        experienceRecruitment.seq,
                        experienceRecruitment.thumbnailUrl,
                        experienceRecruitment.title,
                        experienceRecruitment.currentJoinCount,
                        experienceRecruitment.endDate
                ))
                .from(experienceRecruitment)
                .where(experienceRecruitment.createUser.eq(user))
                .orderBy(experienceRecruitment.seq.desc())
                .limit(3)
                .fetch();

    }


    private BooleanExpression eqCategory(String category) {
        return switch (category) {
            case "APP" -> experienceRecruitment.category.eq(ExperienceRecruitmentCategory.APP);
            case "GAME" -> experienceRecruitment.category.eq(ExperienceRecruitmentCategory.GAME);
            case "SERVICE" -> experienceRecruitment.category.eq(ExperienceRecruitmentCategory.SERVICE);
            default -> throw new CustomException(Code.BAD_REQUEST);
        };
    }

}
