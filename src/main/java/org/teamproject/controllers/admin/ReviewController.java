package org.teamproject.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.configs.ConfigInfoService;
import org.teamproject.commons.configs.ConfigSaveService;

@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewController {

    @GetMapping
    public String review(Model model) {
        return "admin/review";
    }

    @PostMapping
    public String reviewPs(Model model) {

        return "admin/review";
    }

    private void commonProcess(Model model) {
        String title = "고객 문의/리뷰";
        String menuCode = "review";
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", menuCode);
    }
}
