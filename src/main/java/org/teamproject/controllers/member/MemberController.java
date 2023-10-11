package org.teamproject.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.Utils;
import org.teamproject.entities.Member;
import org.teamproject.models.member.UserInfoService;
import org.teamproject.models.member.UserSaveService;

import java.security.SecureRandom;
import java.util.Optional;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements CommonProcess {

    private final UserSaveService saveService;
    private final Utils utils;

    @Autowired
    private final UserInfoService userInfoService;


    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm form, Model model) {
        commonProcess(model, "회원가입");
        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm form, Errors errors, Model model) {
        commonProcess(model, "회원가입");

        saveService.save(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess(model, "로그인");

        return utils.tpl("member/login");


    }

    @PostMapping("/findUserNm")
    public ResponseEntity<String> findUserNmByEmail(@RequestParam String email) {
        Optional<String> userNmOptional = userInfoService.findUserNmByEmail(email);

        return userNmOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    @PostMapping("/findPassword")
    public ResponseEntity<String> findPassword(@RequestParam String userNm, @RequestParam String email) {
        Optional<Member> memberOptional = userInfoService.findMemberByUserNmAndEmail(userNm, email);

        return memberOptional
                .map(member -> {
                    // 비밀번호 변경 로직 수행
                    String newPassword = generateNewPassword();
                    userInfoService.updatePassword(userNm, email, newPassword);

                    return ResponseEntity.ok("Password reset successful. New password: " + newPassword);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    private String generateNewPassword() {
        int length = 30;  // 새로운 비밀번호의 길이
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";  // 사용할 문자들
        SecureRandom random = new SecureRandom();

        byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        StringBuilder newPassword = new StringBuilder(length);
        for (byte b : bytes) {
            newPassword.append(chars.charAt(Math.abs(b % chars.length())));
        }

        return newPassword.toString();
    }


    @GetMapping("/myInfo")
    public String memberInfo(){

        return "member/myInfo";
    }

}

