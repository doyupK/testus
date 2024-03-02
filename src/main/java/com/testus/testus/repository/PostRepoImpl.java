package com.testus.testus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.Post;
import com.testus.testus.domain.User;
import com.testus.testus.dto.post.PostThumbnailDto;
import com.testus.testus.enums.TestCategory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

import static com.testus.testus.domain.QPost.post;


public class PostRepoImpl extends QuerydslRepositorySupport implements PostRepoCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.queryFactory = jpaQueryFactory;
    }


    @Override
    public List<PostThumbnailDto> getLatestPost(String category) {
        return queryFactory.select(
                        Projections.constructor(PostThumbnailDto.class,
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
    public List<PostThumbnailDto> getPopularPost(String category) {
        return queryFactory.select(
                        Projections.constructor(PostThumbnailDto.class,
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
    public List<Post.MyPostDataResponse> getMyTest(User user) {
        return queryFactory.select(
                Projections.constructor(
                        Post.MyPostDataResponse.class,
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
        )
    }


    private BooleanExpression eqCategory(String category) {
        return switch (category) {
            case "APP" -> post.category.eq(TestCategory.APP);
            case "GAME" -> post.category.eq(TestCategory.GAME);
            case "SERVICE" -> post.category.eq(TestCategory.SERVICE);
            default -> throw new CustomException(Code.BAD_REQUEST);
        };
    }

}
