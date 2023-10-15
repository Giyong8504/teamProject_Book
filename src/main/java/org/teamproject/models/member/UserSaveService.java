package org.teamproject.models.member;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.teamproject.commons.constants.Role;
import org.teamproject.controllers.member.JoinForm;
import org.teamproject.entities.Member;
import org.teamproject.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class UserSaveService {
    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    public void save(JoinForm joinForm, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        Member member = new ModelMapper().map(joinForm, Member.class);
        member.setRole(Role.USER);
        save(member);
    }

    public void save(Member member) {
        String mobile = member.getMobile();
        if (mobile != null && !mobile.isBlank()) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        repository.saveAndFlush(member);
    }
}
