package org.teamproject.configs.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        init();

        return true;
    }

    private void init() {
        /**
         * PC, MOBILE 구분 -> ?device=
         */
        String device = request.getParameter("device");
        if (device != null) {
            HttpSession session = request.getSession();
            device = device.equals("mobile") ? "mobile" : "pc";
            session.setAttribute("device", device);
        }

    }
}
