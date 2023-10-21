package org.teamproject.controllers.admin.products;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.teamproject.commons.CommonProcess;
import org.teamproject.commons.Menu;
import org.teamproject.commons.ScriptExceptionProcess;
import org.teamproject.commons.Utils;
import org.teamproject.commons.constants.BookStatus;
import org.teamproject.controllers.admin.dtos.CategoryForm;
import org.teamproject.controllers.admin.dtos.ProductForm;
import org.teamproject.entities.product.Category;
import org.teamproject.models.categories.CategoryInfoService;
import org.teamproject.models.categories.CategoryRegistService;
import org.teamproject.models.categories.CategoryUpdateService;
import org.teamproject.models.product.ProductInfoService;
import org.teamproject.models.product.ProductSaveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController implements CommonProcess, ScriptExceptionProcess {

    private final HttpServletRequest request;
    private final CategoryRegistService categoryRegistService;
    private final CategoryInfoService categoryInfoService;
    private final CategoryUpdateService categoryUpdateService;
    private final ProductSaveService productSaveService;
    private final ProductInfoService productInfoService;

    private Utils utils;
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
     * 상품 등록
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(@ModelAttribute ProductForm form, Model model) {
        commonProcess("add", model);

        return "admin/product/add";
    }

    /**
     * 상품 수정
     * @param bookNo
     * @param model
     * @return
     */

    @GetMapping("/{bookNo}")
    public String update(@PathVariable Long bookNo, Model model) {
        commonProcess("edit", model);

        ProductForm form = productInfoService.getFormData(bookNo);
        log.info(form.toString());
        model.addAttribute("productForm", form);

        return "admin/product/edit";
    }


    @PostMapping("/save")
    public String bookSave(@Valid ProductForm form, Errors errors, Model model) {
        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        commonProcess(mode, model);

        if (errors.hasErrors()) {
            return "admin/product/" + mode;
        }

        productSaveService.save(form);

        String script = String.format("parent.location.replace('%s');", "/admin/product");
        model.addAttribute("script", script);

        return "common/_execute_script";
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
        String pageTitle = "상품 조회/수정";
        if (mode.equals("category")) {
            pageTitle = "상품 분류";
        }else if (mode.equals("add")) {
            pageTitle = "상품 등록";
        }else if (mode.equals("edit")) {
            pageTitle = "상품정보 수정";
        }


        CommonProcess.super.commonProcess(model, pageTitle);
        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCommonCss = new ArrayList();
        List<String> addCss = new ArrayList<>();

        if (mode.equals("add") || mode.equals("edit")) {
            addCommonScript.add("ckeditor/ckeditor");
            addCommonScript.add("fileManager");
            addScript.add("product/form");
            model.addAttribute("statusList", BookStatus.getList());

            List<Category> categories = categoryInfoService.getAll("all");
            model.addAttribute("categories", categories);
        }

        // 모델에 데이터 추가
        model.addAttribute("menuCode", "product");

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCss", addCss);

        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);
        model.addAttribute("submenus", Menu.gets("product"));
    }
}
