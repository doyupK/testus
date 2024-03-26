package com.testus.testus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testus.testus.domain.ExperienceRecruitmentImage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ExperienceRecruitmentImageRepoImpl extends QuerydslRepositorySupport implements ExperienceRecruitmentImageRepoCustom {

    private final JPAQueryFactory jpaQueryFactory;
    public ExperienceRecruitmentImageRepoImpl(JPAQueryFactory jpaQueryFactory) {
        super(ExperienceRecruitmentImage.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
