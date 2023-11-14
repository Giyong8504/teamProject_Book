package org.teamproject.controllers.member;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class JoinForm {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min=8)
    private String userPw;
    @NotBlank
    private String userPwRe;
    @NotBlank
    private String userNm;
    @NotBlank
    private String mobile;
    @NotBlank
    private String zonecode;
    @NotBlank
    private String address;
    @NotBlank
    private String addressSub;
    @AssertTrue
    private Boolean agree;

    /* agree 필드에 null값이 들어가는 에러가 발생하여 setter 메서드 이용하여 false로 초기화 */
    public void setAgree(Boolean agree) {
        this.agree = agree;
    }
}
