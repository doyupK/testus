package com.testus.testus.repository;

import com.testus.testus.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepo extends JpaRepository<Notice, Integer> {
}
