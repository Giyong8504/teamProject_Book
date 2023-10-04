package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamproject.commons.AlertBackException;
import org.teamproject.commons.CommonException;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.ScriptExceptionProcess;
import org.teamproject.entities.Books;
import org.teamproject.models.product.CategoryInfoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class BookController implements CommonProcess, ScriptExceptionProcess {
    private final CategoryInfoService categoryInfoService;

    @GetMapping("/view/{bookNo}")
    public String view(@PathVariable Long bookNo, Model model) {
        try {
            Books data = categoryInfoService.get(bookNo);
            commonProcess(model, "view", data.getBookNm());

            model.addAttribute("data", data);
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertBackException(e.getMessage());
        }


        return "book/view";
    }


    @Override
    public void commonProcess(Model model, String mode) {
        commonProcess(model, mode, null);
    }

    public void commonProcess(Model model, String mode, String addtitle) {
        String pageTitle = "";
        if (mode.equals("view")) {
            if (addtitle != null && !addtitle.isBlank()) addtitle += "|";
            pageTitle = addtitle;
        }

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        if (model.equals("view")) {
            addScript.add("/front/js/book/view");
            addCss.add("/front/js/order/cart");
        }

        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
}
