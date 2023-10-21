package org.teamproject.controllers.orders;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.*;
import org.teamproject.entities.CartInfo;
import org.teamproject.models.orders.CartInfoService;
import org.teamproject.models.orders.CartItemNotFoundException;
import org.teamproject.models.orders.OrderSaveService;
import org.teamproject.models.member.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements CommonProcess, ScriptExceptionProcess { // admin이 아닌 member의 주문 Controller (장바구니 -> 주문)
    private final CartInfoService cartInfoService;
    private final OrderSaveService saveService;
    private final MemberUtil memberUtil;

    @GetMapping
    public String index(@ModelAttribute OrderForm form, Model model) {
        try {
            commonProcess(model, "form", form);

            return "order/index";
        } catch (CommonException e) {
            throw new AlertBackException(e.getMessage()); // 뒤로 back
        }
    }

    @PostMapping
    public String indexPs(@Valid OrderForm form, Errors errors, Model model) {
        commonProcess(model, "form", form);

        if (errors.hasErrors()) {
            return "order/index";
        }

        saveService.save(form);

        // 예외발생 -> alter / 예외발생 X -> 결제 진행
        // 결제 진행
        model.addAttribute("script", "processPay()");
        return "order/index";
    }

    public void commonProcess(Model model, String mode) {
        commonProcess(model, mode, null);
    }

    public void commonProcess(Model model, String mode, OrderForm form) { // 수정필요
        String pageTitle = "주문서 작성";
        if (mode.equals("end")) pageTitle = "주문 완료";
        else if (mode.equals("view")) pageTitle = "주문서 확인";

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();
        if (mode.equals("form")) {
            addCommonScript.add("address");
            addScript.add("order/order");

            /* 주문서 양식인 경우 장바구니 상품 조회 S */
            List<Long> cartNo = form.getCartNo();
            List<CartInfo> items = null;
            if (cartNo == null || cartNo.isEmpty()) { // 바로구매
                items = cartInfoService.getList("direct");
            } else { // 장바구니 또는 cartNo로 구매
                items = cartInfoService.getList(cartNo);
            }

            if (items == null || items.isEmpty()) {
                throw new CartItemNotFoundException();
            }

            int totalPrice = cartInfoService.getTotalPrice(items);
            form.setTotalPrice(totalPrice);

            model.addAttribute("items", items);
            /* 주문서 양식인 경우 장바구니 상품 조회 E */

            /* 로그인한 경우 - 회원정보로 주문자 정보 완성 */
            if (memberUtil.isLogin()) {
                UserInfo member = memberUtil.getMember();
                form.setOrderName(Objects.requireNonNullElse(form.getOrderName(), member.getUserNm()));
                form.setOrderEmail(Objects.requireNonNullElse(form.getOrderEmail(), member.getEmail()));
                form.setOrderMobile(Objects.requireNonNullElse(form.getOrderMobile(), member.getMobile()));
            }

        }

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
