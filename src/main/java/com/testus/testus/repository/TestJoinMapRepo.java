package com.testus.testus.repository;

import com.testus.testus.domain.TestJoinMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestJoinMapRepo extends JpaRepository<TestJoinMapping, Long>, TestJoinMapRepoCustom {
}
