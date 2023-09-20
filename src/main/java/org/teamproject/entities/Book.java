package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Book extends BaseEntity{

    @Id @GeneratedValue
    private Long bookNo;

    @Column(nullable = false, unique = true)
    private String bookId;

    @Column(length = 100, nullable = false)
    private String bookNm;

    @Column(nullable = false)
    private Long price;
}
