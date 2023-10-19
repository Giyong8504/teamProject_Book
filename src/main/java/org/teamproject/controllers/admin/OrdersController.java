package org.teamproject.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.ListData;
import org.teamproject.commons.Menu;
import org.teamproject.controllers.orders.OrdersSearch;
import org.teamproject.entities.OrderInfo;
import org.teamproject.models.orders.OrdersInfoService;
import org.teamproject.models.product.ProductSearch;


@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrdersController implements CommonProcess { /** 관리자 페이지 */

    private final HttpServletRequest request;
    private final OrdersInfoService ordersInfoService;

    @GetMapping
    public String index(@ModelAttribute OrdersSearch ordersSearch, Model model, @ModelAttribute ProductSearch productSearch) {
        commonProcess("list",model);
        ListData<OrderInfo> data = ordersInfoService.getList(ordersSearch);
        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return "admin/orders/index";
    }

    @GetMapping("/newOrder") // 신규 주문
    public String newOrder(Model model) {
        commonProcess("newOrder", model);
        return "admin/orders/newOrder";
    }

    @GetMapping("/prepare") // 배송 준비중
    public String prepare(Model model) {
        commonProcess("prepare", model);
        return "admin/orders/prepare";
    }

    @GetMapping("/status") // 배송 현황
    public String state(Model model) {
        commonProcess("status", model);
        return "admin/orders/status";
    }

    @GetMapping("/delivered") // 배송 완료
    public String delivered(Model model) {
        commonProcess("delivered", model);
        return "admin/orders/delivered";
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
        model.addAttribute("submenus", Menu.gets("orders"));
    }
}
