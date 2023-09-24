package org.teamproject.controllers.InquiryReview;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.InquiryReview.InquiryBoardService;
import org.teamproject.entities.InquiryBoard;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryBoardService boardService;

    @GetMapping("/InquiryWrite")
    public String InquiryWriteForm() {
        return "front/InquiryReview/InquiryWrite";
    }

    @PostMapping("/InquiryWrite") //문의 작성 후 넘어갈 html 필요
    public String InquiryList(InquiryBoard board) {
        boardService.write(board);
        return "";
    }

    @GetMapping("/admin/review")
    public String review(Model model) {
        model.addAttribute("list", boardService.inquiryList());
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