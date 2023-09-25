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
import org.teamproject.commons.ScriptExceptionProcess;
import org.teamproject.controllers.admin.dtos.CategoryForm;
import org.teamproject.entities.product.Category;
import org.teamproject.models.product.CategoryInfoService;
import org.teamproject.models.product.CategoryRegistService;
import org.teamproject.models.product.CategoryUpdateService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController implements CommonProcess, ScriptExceptionProcess {
    private final HttpServletRequest request;
    private final CategoryRegistService categoryRegistService;
    private final CategoryInfoService categoryInfoService;
    private final CategoryUpdateService categoryUpdateService;

    /**
     * 상품 목록
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        commonProcess("list", model);

        return "admin/product/index";
    }

    /**
     * 상품 분류 등록, 조회, 수정,
     * @param model
     * @return
     */
    @GetMapping("/category")
    public String category(Model model) {
        commonProcess("category", model);

        List<Category> items = categoryInfoService.getAll("all");
        model.addAttribute("items", items);

        return "admin/product/category";
    }

    /**
     * 분류 등록, 수정, 삭제 처리
     *
     * @param model
     * @return
     */
    @PostMapping("/category")
    public String categoryPs(CategoryForm form, Model model) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        if (mode.equals("register")) { // 카테고리 등록
            categoryRegistService.regist(form);
        } else if (mode.equals("edit")) { // 카테고리 목록 수정
            categoryUpdateService.update(form);
        }

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    public void commonProcess(String mode, Model model) {
        String pageTitle = "상품관리";
        if (mode.equals("category")) pageTitle = "상품 분류";


        CommonProcess.super.commonProcess(model, pageTitle);


        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);
        model.addAttribute("submenus", Menu.gets("product"));
    }
}
