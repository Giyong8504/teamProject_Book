package org.teamproject.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;

    public boolean isMobile() {
        String device = (String)session.getAttribute("device");

        if (device != null) return device.equals("mobile");

        String ua = request.getHeader("User-Agent");
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        boolean isMobile = ua.matches(pattern);

        return isMobile;
    }

    public String tpl(String path) {

        String tpl = isMobile() ? "mobile/" : "front/";
        tpl += path;

        return tpl;
    }
}
