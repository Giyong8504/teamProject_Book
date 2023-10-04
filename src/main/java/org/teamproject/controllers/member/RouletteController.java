package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RouletteController {

    @GetMapping("/member/roulette")
    public String roulette (Model model){
        return "front/member/roulette";

    }
}
