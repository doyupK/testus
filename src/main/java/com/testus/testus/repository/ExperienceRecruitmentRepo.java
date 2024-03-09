package com.testus.testus.repository;

import com.testus.testus.domain.ExperienceRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRecruitmentRepo extends JpaRepository<ExperienceRecruitment, Integer>, ExperienceRecruitmentRepoCustom {
}
