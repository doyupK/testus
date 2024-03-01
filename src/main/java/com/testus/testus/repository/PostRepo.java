package com.testus.testus.repository;

import com.testus.testus.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer>, PostRepoCustom {
}
