package org.teamproject.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.Utils;
import org.teamproject.entities.Books;
import org.teamproject.entities.Member;
import org.teamproject.models.member.UserSaveService;
import org.teamproject.repositories.MemberRepository;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@RestController//
public class MemberController implements CommonProcess {

    private final UserSaveService saveService;
    private final Utils utils;

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

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/data")
    public String member (Model model){
        List<Member> ageDistribution = memberRepository.findAll();
        model.addAttribute("10대,20대,30대,40대,50대",ageDistribution);
        return "data";

        /** 데이터를 가져와 json
         *  방식으로  반환준비
         *
         *
         *
         */

    }

    @GetMapping("/book")
    public String insert_Book(@ModelAttribute BookForm bookForm, Model model){
        commonProcess(model, "책 등록");
        return utils.tpl("member/book");
    }

}
