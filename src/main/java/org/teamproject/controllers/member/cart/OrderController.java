package org.teamproject.controllers.member.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.AlertBackException;
import org.teamproject.commons.CommonException;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.ScriptExceptionProcess;
import org.teamproject.entities.CartInfo;
import org.teamproject.models.cart.CartInfoService;
import org.teamproject.models.cart.CartItemNotFoundException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements CommonProcess, ScriptExceptionProcess { // admin이 아닌 member의 주문 Controller (장바구니 -> 주문)
    private final CartInfoService cartInfoService;

    @GetMapping
    public String index(@ModelAttribute OrderForm form, Model model) {
        try {
//            List<CartInfo> items = null;
//            if (cartNo == null && cartNo.length == 0) { // 바로구매
//                items = cartInfoService.getList("direct");
//            } else { // 장바구니 또는 cartNo로 구매
//                items = cartInfoService.getList(cartNo);
//            }
//
//            if (items == null && !items.isEmpty()) {
//                throw new CartItemNotFoundException();
//            }
//
//            model.addAttribute("items", items);
//

            commonProcess(model, "form", form);

            return "order/index";
        } catch (CommonException e) {
            throw new AlertBackException(e.getMessage()); // 뒤로 back
        }
    }

    public void commonProcess(Model model, String mode, OrderForm form) { // 수정필요
        String pageTitle = "주문서 작성";
        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        if (mode.equals("form")) {
            addScript.add("order/order");
        }

        model.addAttribute("addScript", addScript);
    }
}
