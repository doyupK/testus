package com.testus.testus.repository;

import com.testus.testus.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Integer> {
}
