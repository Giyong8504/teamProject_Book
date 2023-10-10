package org.teamproject.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.Menu;
import org.teamproject.commons.configs.ConfigInfoService;
import org.teamproject.commons.configs.ConfigSaveService;

@Controller
@RequestMapping("/admin/claim")
@RequiredArgsConstructor
public class ClaimController implements CommonProcess {
    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;
    private final HttpServletRequest request;

    @GetMapping
    public String index(Model model) {
        commonProcess("list", model);
       // ConfigForm configForm = infoService.get(code, ConfigForm.class);

       // model.addAttribute("configForm", configForm == null ? new ConfigForm() : configForm);
        return "admin/claim/index";   // /access
    }

    @GetMapping("/cancel") // 취소
    public String cancel(Model model) {
        commonProcess("cancel", model);
        return "admin/claim/cancel";
    }

    @GetMapping("/change") // 교환
    public String change(Model model) {
        commonProcess("change", model);
        return "admin/claim/change";
    }

    @GetMapping("/takeBack") // 반품
    public String takeBack(Model model) {
        commonProcess("takeBack", model);
        return "admin/claim/takeBack";
    }

    public void commonProcess(String mode, Model model) {
        String pageTitle = "취소/교환/반품";
        if (mode.equals("cancel")) {
            pageTitle = "취소";
        } else if (mode.equals("change")) {
            pageTitle = "교환";
        } else if (mode.equals("takeBack")) {
            pageTitle = "반품";
        }

        CommonProcess.super.commonProcess(model, pageTitle);

        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);
        model.addAttribute("submenus", Menu.gets("claim"));
    }
}
