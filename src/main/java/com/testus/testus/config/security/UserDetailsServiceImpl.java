package com.testus.testus.config.security;

import com.testus.testus.domain.User;
import com.testus.testus.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public final UserRepo UserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepo.findById(Integer.valueOf(username)).orElseThrow(() -> new UsernameNotFoundException("회원 없음"));
        return new UserDetailsImpl(user);
    }
}
