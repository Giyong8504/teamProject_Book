package org.teamproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.teamproject.commons.CommonException;

/**
 * 컨트롤러에서 발생하는 예외를 처리하기 위한 공통 컨트롤러 클래스.
 */
@ControllerAdvice("org.teamproject.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    /**
     * 예외 핸들러 메소드. 컨트롤러 내에서 예외가 발생하면 해당 메소드가 호출.
     */
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response) {

        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 기본적으로 500 (서버 내부 오류) 상태 코드
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus().value(); // 예외 객체가 CommonException인 경우 해당 예외의 HTTP 상태 코드를 사용
        }

        response.setStatus(status); // 응답에 상태 코드 설정
        String URL = request.getRequestURI(); // 요청한 URL

        // 모델에 오류와 관련된 정보 추가
        model.addAttribute("status", status); // 상태 코드
        model.addAttribute("path", URL); // 요청한 경로
        model.addAttribute("message", e.getMessage()); // 예외 메시지
        model.addAttribute("exception", e); // 예외 객체

        e.printStackTrace(); // 예외 스택 트레이스 출력

        return "error/common"; // 오류 페이지의 뷰 이름 반환
    }
}