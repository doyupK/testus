package com.testus.testus.repository;

import com.testus.testus.domain.Report;
import com.testus.testus.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Integer>, ReportRepoCustom {

}
