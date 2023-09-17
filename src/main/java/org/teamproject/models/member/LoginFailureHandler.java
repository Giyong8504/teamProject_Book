package org.teamproject.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * 로그인 인증 실패 시 실행될 핸들러 클래스
 * Spring Security의 AuthenticationFailureHandler 인터페이스를 구현
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 로그인 인증 실패 시 호출되는 메서드
     * 인증 실패 원인에 따라 세션에 관련 정보를 저장하고 로그인 페이지로 이동
     * @param request 인증 요청을 포함한 HttpServletRequest 객체
     * @param response 인증 응답을 포함한 HttpServletResponse 객체
     * @param exception 인증 실패 원인을 포함한 AuthenticationException 객체
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String userPw = request.getParameter("userPw");

        // 세션에서 관련한 속성 제거
        session.removeAttribute("requiredEmail");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("globalError");

        // 로그인 폼에 입력한 아이디 저장
        session.setAttribute("email", email);

        // 아이디 필드가 빈 값인 경우 에러 메시지 저장
        if (email == null || email.isBlank()) {
            session.setAttribute("requiredEmail", bundle.getString("NotBlank.email"));
        }

        // 비밀번호 필드가 빈 값인 경우 에러 메시지 저장
        if (userPw == null || userPw.isBlank()) {
            session.setAttribute("requiredUserPw", bundle.getString("NotBlank.userPw"));
        }

        // 아이디와 비밀번호 모두 입력한 경우 전체 에러 메시지 저장
        if (email != null && !email.isBlank() && userPw != null && !userPw.isBlank()) {
            session.setAttribute("globalError", bundle.getString("Login.fail"));
        }

        // 로그인 페이지로 리디렉션
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
