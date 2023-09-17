package org.teamproject.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor // Lombok 애너테이션: 파라미터 없는 생성자 자동 생성
@AllArgsConstructor // Lombok 애너테이션: 모든 필드를 파라미터로 받는 생성자 자동 생성
public class JSONData<T> {
    private boolean success;
    private HttpStatus status = HttpStatus.OK; // HTTP 응답 상태 코드, 기본값은 200 OK
    private String message; // 응답 메시지를 담는 문자열
    private T data; // 실제 데이터 객체를 담는 제네릭 필드
}