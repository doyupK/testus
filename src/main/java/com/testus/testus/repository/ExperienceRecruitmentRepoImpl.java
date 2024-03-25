package com.testus.testus.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.User;
import com.testus.testus.dto.post.ExperienceRecruitmentThumbnailDto;
import com.testus.testus.enums.ExperienceRecruitmentCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                                experienceRecruitment.thumbnail
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
                                experienceRecruitment.thumbnail
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
    public Page<ExperienceRecruitment.MyPostDataResponse> getMyTest(User user, Pageable pageable) {
        JPQLQuery<ExperienceRecruitment.MyPostDataResponse> query = queryFactory.select(
                        Projections.constructor(
                                ExperienceRecruitment.MyPostDataResponse.class,
                                experienceRecruitment.seq,
                                experienceRecruitment.thumbnail,
                                experienceRecruitment.title,
                                experienceRecruitment.currentJoinCount,
                                experienceRecruitment.endDate
                        ))
                .from(experienceRecruitment)
                .where(experienceRecruitment.createUser.eq(user))
                .orderBy(experienceRecruitment.seq.desc());
//                .limit(3);
//                .fetch()

        List<ExperienceRecruitment.MyPostDataResponse> testList =
                Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(testList, pageable, query.fetchCount());
    }

    @Override
    public ExperienceRecruitment.TestListResponse getTestList(int lastId, String category, String tag, String sortBy) {
        List<ExperienceRecruitmentThumbnailDto> result =
                queryFactory.select(
                                Projections.constructor(ExperienceRecruitmentThumbnailDto.class,
                                        experienceRecruitment.seq,
                                        experienceRecruitment.title,
                                        experienceRecruitment.thumbnail,
                                        experienceRecruitment.endDate,
                                        experienceRecruitment.currentJoinCount,
                                        experienceRecruitment.joinLimit,
                                        experienceRecruitment.star,
                                        experienceRecruitment.reviewCnt

                                ))
                        .from(experienceRecruitment)
                        .where(eqCategory(category),
                                ltCursorId(lastId)
                        )
                        .limit(9)
                        .orderBy(eqOrder(sortBy))
                        .fetch();

        boolean isLast = result.size() < 9;


        return ExperienceRecruitment.TestListResponse.builder().data(result).isLast(isLast).build();
    }


    private BooleanExpression ltCursorId(Integer lastId) {
        if (lastId == 0) {
            return null;
        } else {
            return experienceRecruitment.seq.lt(lastId);
        }
    }


    @Override
    public ExperienceRecruitment.TestListResponse getTestList(int lastId, String category, String tag, String sortBy, User user) {
        List<ExperienceRecruitmentThumbnailDto> result =
                queryFactory.select(
                                Projections.constructor(ExperienceRecruitmentThumbnailDto.class,
                                        experienceRecruitment.seq,
                                        experienceRecruitment.title,
                                        experienceRecruitment.thumbnail,
                                        experienceRecruitment.endDate,
                                        experienceRecruitment.currentJoinCount,
                                        experienceRecruitment.joinLimit,
                                        experienceRecruitment.star,
                                        experienceRecruitment.reviewCnt

                                ))
                        .from(experienceRecruitment)
                        .where(ltCursorId(lastId), eqCategory(category), eqAvailable(user)

                        )
                        .limit(9)
                        .orderBy(eqOrder(sortBy))
                        .fetch();

        boolean isLast = result.size() < 9;


        return ExperienceRecruitment.TestListResponse.builder().data(result).isLast(isLast).build();
    }

    private BooleanExpression eqAvailable(User user) {
        // TODO: 3/25/24 이어할것 
//        if (user.getGender())
        return null;
    }

    private OrderSpecifier<?> eqOrder(String sortBy) {
        return switch (sortBy) {
            case "popularity" -> new OrderSpecifier<>(Order.DESC, experienceRecruitment.star);
            case "join" -> new OrderSpecifier<>(Order.DESC, experienceRecruitment.currentJoinCount);

            // TODO: 3/25/24 남은 기간 짧은 순 리팩토링 할 것
//            case "endDate" : {
//                return new OrderSpecifier<>(Order.DESC, experienceRecruitment.star);
//            }
            case "latest" -> new OrderSpecifier<>(Order.DESC, experienceRecruitment.createDate);
            default -> throw new IllegalStateException("Unexpected value: " + sortBy);
        };
    }


    private BooleanExpression eqCategory(String category) {
        return switch (category) {
            case "ALL" -> null;
            case "APP" -> experienceRecruitment.category.eq(ExperienceRecruitmentCategory.APP);
            case "GAME" -> experienceRecruitment.category.eq(ExperienceRecruitmentCategory.GAME);
            case "SERVICE" -> experienceRecruitment.category.eq(ExperienceRecruitmentCategory.SERVICE);
            default -> throw new CustomException(Code.BAD_REQUEST);
        };
    }

}
