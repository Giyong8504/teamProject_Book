package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.teamproject.commons.constants.BookStatus;
import org.teamproject.entities.product.Category;

import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Books extends BaseEntity{

    @Id @GeneratedValue
    private Long bookNo;

    @ToString.Exclude
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cateCd")
    private Category category;

    private String isbn;

    @Column(length = 50, nullable = false)
    private String gid;

    @Column(length = 100, nullable = false)
    private String bookNm;
    private String publicationDt;
    private String author;
    private String publisher;
    private String origin;

    private int price;
    private int stock; // 재고

    @Enumerated(EnumType.STRING)
    @Column(length = 25, nullable = false)
    private BookStatus status = BookStatus.READY;   // 판매중 기본값

    @Lob
    private String description;

    private long listOrder;

    @Transient
    private List<FileInfo> mainImages; // 상품 메인 이미지

    @Transient
    private List<FileInfo> listImages; // 목록 이미지

    @Transient
    private List<FileInfo> editorImages; // 에디터 이미지
}