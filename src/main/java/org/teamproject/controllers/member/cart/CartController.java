package org.teamproject.controllers.member.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.AlertException;
import org.teamproject.commons.CommonException;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.ScriptExceptionProcess;
import org.teamproject.models.cart.CartInfoSaveService;

@Controller
@RequestMapping("/order/cart")
@RequiredArgsConstructor
public class CartController implements CommonProcess, ScriptExceptionProcess {
    private final CartInfoSaveService cartInfoSaveService;
    @PostMapping
    public String cartPs(CartForm form, Model model) {
        try {
            cartInfoSaveService.save(form);

            String mode = form.getMode();
            String url = mode.equals("direct") ? "/order/form" : "/order/cart";

            String script = String.format("parent.location.replace('%s');", url);
            model.addAttribute("script", script);

            return "commons/_execute_script";
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertException(e.getMessage());
        }
    }

    public void commonProcess(Model model, String mode) {
        String pageTitle = "장바구니";
        CommonProcess.super.commonProcess(model, pageTitle);
    }
}
