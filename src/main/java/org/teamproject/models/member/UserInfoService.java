package org.teamproject.models.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.teamproject.controllers.member.MemberUpdateDto;
import org.teamproject.entities.Member;
import org.teamproject.repositories.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    // 아이디 찾기
    public Optional<String> findUserNmByEmail(String email) {
        Member member = repository.findByEmail(email);
        return Optional.ofNullable(member).map(Member::getUserNm);
    }

    // 비밀번호 찾기
    public Optional<Member> findMemberByUserNmAndEmail(String userNm, String email) {
        return Optional.ofNullable(repository.findByUserNmAndEmail(userNm, email));
    }

    // 비밀번호 변경
    @Transactional
    public boolean updatePassword(String userNm, String email, String newPassword) {
        try {
            repository.updatePassword(userNm, email, newPassword);
            return true;
        } catch (Exception e) {
            e.printStackTrace();  // 에러 로깅 등 추가적인 처리가 필요하면 여기서 처리 가능
            return false;
        }
    }


    /** 회원정보 수정 **/
    public Long updateMember(MemberUpdateDto memberUpdateDto) {
        Member member = repository.findByEmail(memberUpdateDto.getEmail());
        member.updateUsername(memberUpdateDto.getUserNm());
        member.updateZonecode(memberUpdateDto.getZonecode());
        member.updateAddress(memberUpdateDto.getAddress());
        member.updateAddressSub(memberUpdateDto.getAddressSub());
        member.updatePassword(memberUpdateDto.getUserPw());

        return member.getUserNo();
    }




}
