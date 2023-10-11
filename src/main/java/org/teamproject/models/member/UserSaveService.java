package org.teamproject.models.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.constants.Role;
import org.teamproject.controllers.member.JoinForm;
import org.teamproject.controllers.member.JoinValidator;
import org.teamproject.entities.Member;
import org.teamproject.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class UserSaveService {
    private final MemberRepository repository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;
    private final MemberUtil memberUtil;
    private final UserInfoService infoService;
    private final HttpSession session;

    public void save(JoinForm joinForm, Errors errors) {
        validator.validate(joinForm, errors);

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
        System.out.println(member);
        repository.saveAndFlush(member);
    }

    public void update(JoinForm form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(System.out::println);

            return;
        }

        Member member = memberUtil.getEntity();
        member.setUserNm(form.getUserNm());
        member.setMobile(form.getMobile());
        member.setAddress(form.getAddress());
        member.setAddressSub(form.getAddressSub());
        member.setZonecode(form.getZonecode());

        String userPw = form.getUserPw();
        String userPwRe = form.getUserPwRe();
        if (userPw != null && !userPw.isBlank()) {
            if (userPwRe == null || userPwRe.isBlank()) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPwRe", "NotBlank");
                return;
            }

            if (!userPw.equals(userPwRe)) {
                errors.rejectValue("userPwRe", "Mismatch");
                return;
            }
            
            // 비밀번호 변경 처리
            member.setUserPw(encoder.encode(userPw));
        }

        save(member);
        UserInfo userInfo = (UserInfo)infoService.loadUserByUsername(member.getEmail());
        session.setAttribute("userInfo", userInfo);

    }

}
