package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.teamproject.commons.constants.BookStatus;
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

    @Column(length = 50)
    private String category;

    @Column(length = 50, nullable = false)
    private String gid;

    private String bookDesc; // 상품에 대한 설명
    private int price;
    private int stock; // 재고

    @Enumerated(EnumType.STRING)
    @Column(length = 25, nullable = false)
    private BookStatus status;

    @Lob
    private String description;

    private long listOrder;

    @ManyToOne
    @JoinColumn(name="user_email")
    private Member email; // 판매자 아이디

    @Enumerated(EnumType.STRING)
    private Role role;
}