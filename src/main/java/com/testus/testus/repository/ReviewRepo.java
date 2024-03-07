package com.testus.testus.repository;

import com.testus.testus.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Integer>, ReviewRepoCustom {
}
