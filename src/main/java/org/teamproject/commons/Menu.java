package org.teamproject.commons;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    /**
     * 주어진 코드에 따라 메뉴 목록을 반환.
     *
     * @param code 메뉴 코드
     * @return 주어진 메뉴 코드에 따른 메뉴 목록
     */
    public static List<MenuDetail> gets(String code) {
        List<MenuDetail> menus = new ArrayList<>();

        // 회원 하위 메뉴
        if (code.equals("member")) {
            menus.add(new MenuDetail("member", "회원 목록", "/admin/member"));
        } else if (code.equals("product")) { // 상품관리 하위 메뉴
            menus.add(new MenuDetail("product", "상품 목록", "/admin/product"));
            menus.add(new MenuDetail("add", "상품 등록", "/admin/product/add"));
            menus.add(new MenuDetail("edit", "상품 수정", "/admin/product/edit"));
            menus.add(new MenuDetail("category", "상품 분류", "/admin/product/category"));
        } else if (code.equals("order")) { // 주문관리 하위 메뉴
            menus.add(new MenuDetail("order", "주문 관리", "/admin/order"));
            menus.add(new MenuDetail("newOrder", "신규 주문", "/admin/order/newOrder"));
            menus.add(new MenuDetail("prepare", "배송 준비중", "/admin/order/prepare"));
            menus.add(new MenuDetail("status", "배송 현황", "/admin/order/status"));
            menus.add(new MenuDetail("delivered", "배송 완료", "/admin/order/delivered"));
        }

        // 게시판 하위 메뉴
        if (code.equals("board")){
            menus.add(new MenuDetail("board", "게시판 목록", "/admin/board"));
            menus.add(new MenuDetail("register", "게시판 등록/수정", "/admin/board/register"));
            menus.add(new MenuDetail("posts", "게시글 관리", "/admin/board/posts"));
        }

        return menus;
    }

    /**
     * 주어진 요청에 대한 하위 메뉴 코드를 반환.
     *
     * @param request HttpServletRequest 객체
     * @return 요청에 대한 하위 메뉴 코드
     */
    public static String getSubMenuCode(HttpServletRequest request) {
        String URI = request.getRequestURI();

        return URI.substring(URI.lastIndexOf('/') + 1);
    }
}
