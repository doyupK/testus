package com.testus.testus.repository;

import com.testus.testus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer>, UserRepoCustom {
    Optional<User> findByProviderSubject(String id);

    Optional<User> findOneByUserEmail(String userEmail);

    Optional<User> findOneByUserEmailAndPhoneNumber(String userName, String phoneNumber);

    Optional<User> findOneByUserNameAndPhoneNumber(String userName, String phoneNumber);

}
