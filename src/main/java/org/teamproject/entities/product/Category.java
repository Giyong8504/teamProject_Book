package org.teamproject.entities.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.entities.BaseMemberEntity;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_category_listOrder", columnList = "listOrder DESC, regDt DESC")
})
public class Category extends BaseMemberEntity {
    @Id
    @Column(length=40)
    private String cateCd;

    @Column(length=60, nullable = false)
    private String cateNm;

    private boolean open;

    private long listOrder;
}
