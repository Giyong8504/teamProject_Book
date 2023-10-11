package org.teamproject.controllers.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoinForm {
    private String mode;
    @Email
    private String email;
    private String userPw;

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

    private boolean agree;
}
