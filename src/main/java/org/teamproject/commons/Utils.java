package org.teamproject.commons;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;

    public boolean isMobile() {
        String ua = request.getHeader("User-Agent");

        String pattern = ".*Mobile|iP(hone|od|ad)|Android|BlackBerry|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Blazer|Dolfin|Dolphin|Skyfire|Zune.*";

        boolean isMobile = ua.matches(pattern);

        return isMobile;
    }

    public String tpl(String path) {

        String tpl = isMobile() ? "mobile/" : "front/";
        tpl += path;

        return tpl;
    }
}
