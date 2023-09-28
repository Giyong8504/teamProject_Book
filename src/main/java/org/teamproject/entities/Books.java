package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.commons.constants.Role;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Books extends BaseEntity{

    @Id @GeneratedValue
    private Long bookNo;

    @Column(nullable = false, updatable = false)
    private String bookId;

    @Column(length = 100, nullable = false)
    private String bookNm;

    private String bookDesc; // 상품에 대한 설명

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name="user_email")
    private Member email; // 판매자 아이디

    @Enumerated(EnumType.STRING)
    private Role role;
}