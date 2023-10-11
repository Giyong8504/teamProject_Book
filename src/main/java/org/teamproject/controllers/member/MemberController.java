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
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.Utils;
import org.teamproject.entities.Member;
import org.teamproject.models.member.UserInfoService;
import org.teamproject.models.member.UserSaveService;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements CommonProcess {

    private final UserSaveService saveService;
    private final Utils utils;
    private final MemberUtil memberUtil;

    @Autowired
    private final UserInfoService userInfoService;


    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm form, Model model) {
        commonProcess("join", model);
        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm form, Errors errors, Model model) {
        commonProcess("join", model);

        saveService.save(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);

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
    public String memberInfo(Model model){
        commonProcess("info", model);

        JoinForm joinForm = memberUtil.getJoinForm();
        joinForm.setMode("update");
        model.addAttribute("joinForm", joinForm);
        model.addAttribute("mode", "update");

        return utils.tpl("member/myInfo");
    }

    @PostMapping("/myInfo")
    public String memberInfoPs(@Valid JoinForm form, Errors errors, Model model) {
        commonProcess("info", model);
        form.setMode("update");
        form.setEmail(memberUtil.getMember().getEmail());

        saveService.update(form, errors);

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(System.out::println);
            return utils.tpl("member/myInfo");
        }



        return "redirect:/mypage";
    }

    public void commonProcess(String mode, Model model) {
        String pageTitle = "";
        mode = Objects.requireNonNullElse(mode, "");
        if (mode.equals("join")) {
            pageTitle = "회원가입";
        } else if (mode.equals("info")) {
            pageTitle = "회원정보수정";
        } else if (mode.equals("login")) {
            pageTitle = "로그인";
        } else if (mode.equals("findId")) {
            pageTitle = "아이디 찾기";
        } else if (mode.equals("findPw")) {
            pageTitle = "비밀번호 찾기";
        }
            
        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addCss = new ArrayList<>();
        addCss.add("member/style");

        model.addAttribute("addCss", addCss);
    }
}

