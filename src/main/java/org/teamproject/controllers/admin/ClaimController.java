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
@RequestMapping("/admin/claim")
@RequiredArgsConstructor
public class ClaimController {
    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    private String code = "siteConfig";

    @GetMapping
    public String config(Model model) {
        commonProcess(model);
        ConfigForm configForm = infoService.get(code, ConfigForm.class);

        model.addAttribute("configForm", configForm == null ? new ConfigForm() : configForm);
        return "admin/claim";   // /access
    }

    @PostMapping
    public String configPs(ConfigForm configForm, Model model) {
        commonProcess(model);

        saveService.save(code, configForm);

        model.addAttribute("message", "요청이 완료되었습니다."); // 버튼 누르면 메세지 출력

        return "admin/claim";    // /claim/access로 요청 완료된 결과 보이기
    }

    private void commonProcess(Model model) {
        String title = "주문/취소/교환";
        String menuCode = "claim";
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", menuCode);
    }

    /* claim.html 로 attribute 전달 */
}
