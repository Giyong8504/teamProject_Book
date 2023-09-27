package org.teamproject.controllers.statusmember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.entities.Status;
import org.teamproject.repositories.StatusRepository;

import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class StatsController {

    private StatusRepository statusRepository;


    @GetMapping("/admin/stats")
    public String stats(Model model) {
        return "admin/stats";
    }
    

    @PostMapping
    public String statsPs(Model model) {

        return "admin/stats";

    }

    private void commonProcess(Model model) {

        String title = "통계";
        String menuCode = "stats";
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", menuCode);
    }

}


