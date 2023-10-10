package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EventViewController {

    @GetMapping("/member/eventView")
    public String eventView(Model model){
        return "/front/member/eventView";
    }
}
