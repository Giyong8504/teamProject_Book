package org.teamproject.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {
    private static ResourceBundle bundle;
    private static ResourceBundle bundleValidation;
    private static ResourceBundle bundleError;

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
    /**
     * 메세지 조회
     *
     * @param code : 메세지 코드
     * @param type : validation, error, common
     * @return
     */
    public static String getMessage(String code, String type) {
        ResourceBundle _bundle = null;
        if (type.equals("validation")) { // 검증
            if (bundleValidation == null) bundleValidation = ResourceBundle.getBundle("messages.validations");
            _bundle = bundleValidation;
        } else if (type.equals("error")) { // 에러
            if (bundleError == null) bundleError = ResourceBundle.getBundle("messages.errors");
            _bundle = bundleError;
        } else { // 공통
            if (bundle == null) bundle = ResourceBundle.getBundle("messages.commons");
            _bundle = bundle;
        }

        try {
            return _bundle.getString(code);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 단일 요청 데이터 조회
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 복수개 요청 데이터 조회
     *
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }


    public static int getNumber(int num, int defaultValue) {
        return num <= 0 ? defaultValue : num;
    }

    /**
     * 비회원 구분 UID
     * 비회원 구분은 IP + 브라우저 종류
     *
     */
    public int guestUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }

    /**
     * 사이트 설정 조회
     * @param name
     * @return
     */
    public String getConfig(String name) {
        Map<String, String> siteConfig = (Map<String, String>)request.getAttribute("siteConfig");
        String value = siteConfig == null ? "" : siteConfig.get(name);

        return value;
    }
}
