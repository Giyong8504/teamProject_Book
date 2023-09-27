package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EventController {

    @GetMapping("/member/event")
    public String event(Model model) {
        return "front/member/event";
    }
}
