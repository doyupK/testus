package com.testus.testus.repository;

import com.testus.testus.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Test, Integer> {
}
