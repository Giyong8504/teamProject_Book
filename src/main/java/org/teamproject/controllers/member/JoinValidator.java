package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.teamproject.commons.validators.MobileValidator;
import org.teamproject.repositories.MemberRepository;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator {

    private final MemberRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinForm form = (JoinForm) target;

        /**
         * 0. 비밀번호, 비밀번호 확인 필수 항목 추가, 비밀번호 자리수 8자리 이상
         * 1. 이메일 중복 여부
         * 2. 비밀번호, 비밀번호 확인 일치 여부 체크
         * 3. 휴대전화번호가 있으면 형식 체크
         */
        // 0. 비밀번호, 비밀번호 확인 필수 체크
        String userPw = form.getUserPw();
        String userPwRe = form.getUserPwRe();
        String mode = form.getMode();
        boolean isJoinMode = mode == null || !mode.equals("update") ? true : false;

        if (isJoinMode) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotBlank");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPw", "NotBlank");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPwRe", "NotBlank");
        }

        // 0-1. 비밀번호는 8자리 이상으로 제한
        if (userPw != null && !userPw.isBlank() && userPw.length() < 8) {
            errors.rejectValue("userPw", "Size");
        }

        // 1. 이메일 중복 여부
        String email = form.getEmail();
        if (isJoinMode && email != null && !email.isBlank() && repository.existsByEmail(email)) {
            errors.rejectValue("email", "Duplicated");
        }

        // 2. 비밀번호, 비밀번호 확인 일치 여부 체크

        if (userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank()
            && !userPw.equals(userPwRe)) {
            errors.rejectValue("userPwRe", "Mismatch");
        }

        // 3. 휴대전화번호가 있으면 형식 체크
        String mobile = form.getMobile();
        if (mobile != null && !mobile.isBlank()) {
            if (!checkMobile(mobile)) {
                errors.rejectValue("mobile", "Mobile");
            }

            mobile = mobile.replaceAll("\\D", "");
            form.setMobile(mobile);
        } // endif

        if (isJoinMode && !form.isAgree()) {
            errors.rejectValue("agree", "AssertTrue");
        }
    }
}
