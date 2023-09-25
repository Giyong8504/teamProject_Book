package org.teamproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mainIndexController")
@RequestMapping("/")
public class MainIndexController {
    @GetMapping
    public String index() {
        return "front/index";
    }

    private void commonProcess(Model model) {
        String title = "주문/취소/교환";
        String menuCode = "claim";
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", menuCode);
    }
}
