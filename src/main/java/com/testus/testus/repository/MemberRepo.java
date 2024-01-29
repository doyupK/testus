package com.testus.testus.repository;

import com.testus.testus.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Integer>, MemberRepoCustom {
    Optional<Member> findByProviderSubject(String id);

}
