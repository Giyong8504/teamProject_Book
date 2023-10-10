package org.teamproject.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.Utils;
import org.teamproject.entities.Books;
import org.teamproject.entities.Member;
import org.teamproject.models.member.UserInfoService;
import org.teamproject.models.member.UserSaveService;
import org.teamproject.repositories.MemberRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements CommonProcess {

    private final UserSaveService saveService;
    private final Utils utils;

    @Autowired
    private MemberRepository memberRepository;

    private final UserInfoService userInfoService;
    private final PasswordEncoder passwordEncoder;


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


    @GetMapping("/book")
    public String insert_Book(@ModelAttribute BookForm bookForm, Model model){
        commonProcess(model, "책 등록");
        return utils.tpl("member/book");
    }


    @GetMapping("/UserInfo")
    public String memberInfo(Principal principal, ModelMap modelMap){
        String userNM = principal.getName();
        Member member = memberRepository.findByEmail(userNM);
        modelMap.addAttribute("member", member);

        return "member/myInfo";
    }

}

