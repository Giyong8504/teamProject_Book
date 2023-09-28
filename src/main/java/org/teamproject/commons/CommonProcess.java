package org.teamproject.commons;

import org.springframework.ui.Model;

/**
 * 컨트롤러에서 공통적으로 처리하는 작업을 담당하는 인터페이스.
 */
public interface CommonProcess {
    /**
     * 공통 처리 메소드.
     * 주어진 Model에 pageTitle 속성을 추가하여 뷰 템플릿에서 페이지 제목을 설정.
     * @param model Model 객체
     * @param pageTitle 페이지 제목
     */
    default void commonProcess(Model model, String pageTitle) {

        model.addAttribute("pageTitle", pageTitle);
    }
}
