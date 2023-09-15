package org.teamproject.models.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Member;
import org.teamproject.repositories.MemberRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = repository.findByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = Arrays.asList(
          new SimpleGrantedAuthority(member.getRole().name())
        );

        return UserInfo.builder()
                .userNo(member.getUserNo())
                .email(member.getEmail())
                .userPw(member.getUserPw())
                .userNm(member.getUserNm())
                .mobile(member.getMobile())
                .role(member.getRole())
                .authorities(authorities)
                .build();
    }
}
