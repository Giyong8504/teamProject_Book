package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.teamproject.commons.constants.Role;

import java.util.ArrayList;

@Entity
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long userNo;

    @Column(length=100, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false)
    private String userNm;

    @Column(length = 100, nullable = false)
    private int age;

    @Column(length=65, nullable = false)
    private String userPw;

    @Column(length=11, nullable = false)
    private String mobile;

    @Column(length=10, nullable = false)
    private String zonecode;

    @Column(length=100, nullable = false)
    private String address;

    @Column(length=100)
    private String addressSub;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean expired;
    private boolean locked;
    private boolean credentialExpired;
    private boolean enabled;

}
