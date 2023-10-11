package org.teamproject.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.teamproject.models.member.LoginFailureHandler;
import org.teamproject.models.member.LoginSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(f -> {
            f.loginPage("/member/login") // 로그인 페이지 설정
                    .usernameParameter("email") // 사용자 아이디 파라미터명 설정
                    .passwordParameter("userPw") // 사용자 비밀번호 파라미터명 설정
                    .successHandler(new LoginSuccessHandler()) // 로그인 성공 핸들러 설정
                    .failureHandler(new LoginFailureHandler()); // 로그인 실패 핸들러 설정

        });

        http.logout(f -> {
            f.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 URL 설정
                    .logoutSuccessUrl("/member/login"); // 로그아웃 성공 후 이동할 URL 설정
        });

        http.headers(f -> {
            f.frameOptions(o -> o.sameOrigin()); // iframe 보안 설정 (동일 출처에서만 허용)
        });

        http.authorizeHttpRequests(c -> {
                    //.requestMatchers("/admin/**").hasAuthority("ADMIN") // "/admin/" 경로 요청은 'ADMIN' 권한을 가진 사용자만 접근 가능
            c.requestMatchers("/mypage/**", "/member/myInfo/**").authenticated() // "/mypage/" 경로 요청은 인증된 사용자만 접근 가능
                    .anyRequest().permitAll(); // 그 외 모든 요청은 누구나 접근 가능
        });

        http.exceptionHandling(c -> {
            c.authenticationEntryPoint((req, res, e) -> {
                String URI = req.getRequestURI();

                if (URI.indexOf("/admin") != -1) { // 관리자 페이지로 접근 시
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED"); // 401 Unauthorized 에러 반환
                } else { // 회원 전용 페이지로 접근 시
                    String redirectURL = req.getContextPath() + "/member/login"; // 로그인 페이지로 리다이렉트 URL 생성
                    res.sendRedirect(redirectURL); // 로그인 페이지로 리다이렉트
                }
            });
        });

        return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return w -> w.ignoring().requestMatchers(
          "/admin/css/**",
                "/admin/js/**",
                "/admin/images/**",
                "/front/css/**",
                "/front/js/**",
                "/front/images/**",
                "/mobile/css/**",
                "/mobile/js/**",
                "/mobile/images/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
