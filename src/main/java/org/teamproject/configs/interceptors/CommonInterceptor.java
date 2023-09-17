package org.teamproject.configs.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.teamproject.commons.Utils;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final HttpServletRequest request;
    private final Utils utils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        init();

        return true;
    }

    private void init() {
        HttpSession session = request.getSession();
        String URI = request.getRequestURI();
        // 1. 로그인 실패시 세션에 남아 있는 데이터 삭제
        if (URI.indexOf("/member/login") == -1) {
            // 로그인이 아닌 경로로 접속한 경우 불필요한 세션 데이터 삭제
            session.removeAttribute("email");
            session.removeAttribute("requiredEmail");
            session.removeAttribute("requiredUserPw");
            session.removeAttribute("globalError");
        }

        // 2. 현재 접속한 장비 체크하여 템플릿 분리 설정
        /**
         * PC, MOBILE 구분 -> ?device=
         */
        String device = request.getParameter("device");
        if (device != null) {

            device = device.equals("mobile") ? "mobile" : "pc";
            session.setAttribute("device", device);
        }

    }
}
