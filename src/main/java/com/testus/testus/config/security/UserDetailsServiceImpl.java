package com.testus.testus.config.security;

import com.testus.testus.domain.Member;
import com.testus.testus.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public final MemberRepo MemberRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = MemberRepo.findById(Integer.valueOf(username)).orElseThrow(() -> new UsernameNotFoundException("회원 없음"));
        return new UserDetailsImpl(user);
    }
}
