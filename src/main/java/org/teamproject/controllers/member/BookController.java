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
import org.teamproject.models.categories.CategoryInfoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class BookController implements CommonProcess, ScriptExceptionProcess { // 상품 상세 페이지 구현을 위한 Controller
    /* 상품 등록 완료 후에 테스트 진행
        현재는 상품 등록하기가 구현이 안되어있어서 목록을 불러올 수가 없음
    */
    private final CategoryInfoService categoryInfoService;

    /* 확인해주세요.(오류로 인해 주석 처리함.)
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
    */


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

        if (mode.equals("view")) {
            addScript.add("/front/js/book/view");
            addCss.add("/front/js/order/cart");
        }

        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
}
