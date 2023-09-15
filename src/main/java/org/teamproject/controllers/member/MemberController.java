package org.teamproject.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.Utils;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;

    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm form) {

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm form, Errors errors) {

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {

        return utils.tpl("member/login");
    }
}
