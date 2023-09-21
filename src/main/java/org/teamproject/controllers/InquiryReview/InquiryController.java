package org.teamproject.controllers.InquiryReview;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.InquiryReview.InquiryBoardService;
import org.teamproject.entities.InquiryBoard;

@Controller
@RequestMapping("/InquiryWrite")
@RequiredArgsConstructor
public class InquiryController {

    private InquiryBoardService boardService;

    @GetMapping
    public String InquiryWriteForm() {
        return "front/InquiryReview/InquiryWrite";
    }

    @PostMapping("front/InquiryReview/InquiryWrite") //문의 작성 후 넘어갈 html 필요
    public String InquiryList(InquiryBoard board) {
        boardService.write(board);
        return "";
    }
}
