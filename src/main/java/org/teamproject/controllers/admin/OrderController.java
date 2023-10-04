package org.teamproject.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.Menu;
import org.teamproject.models.product.OrderInfoService;


@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderController implements CommonProcess {

    private final HttpServletRequest request;

    @GetMapping
    public String index(Model model) {
        commonProcess("list",model);

        return "admin/order/index";
    }

    @GetMapping("/newOrder") // 신규 주문
    public String newOrder(Model model) {
        commonProcess("newOrder", model);
        return "admin/order/newOrder";
    }

    @GetMapping("/prepare") // 배송 준비중
    public String prepare(Model model) {
        commonProcess("prepare", model);
        return "admin/order/prepare";
    }

    @GetMapping("/status") // 배송 현황
    public String state(Model model) {
        commonProcess("status", model);
        return "admin/order/status";
    }

    @GetMapping("/delivered") // 배송 완료
    public String delivered(Model model) {
        commonProcess("delivered", model);
        return "admin/order/delivered";
    }

    public void commonProcess(String mode,Model model) {
        String pageTitle = "주문 관리";
        if (mode.equals("newOrder")) {
            pageTitle = "신규 주문";
        }else if (mode.equals("prepare")) {
            pageTitle = "배송 준비중";
        }else if (mode.equals("status")) {
            pageTitle = "배송 현황";
        }else if(mode.equals("delivered")) {
            pageTitle = "배송 완료";
        }

        CommonProcess.super.commonProcess(model,pageTitle);

        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);
        model.addAttribute("submenus", Menu.gets("order"));
    }
}