package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
@IdClass(BoardView.class)
public class BoardView {
    @Id
   private Long id;
    @Id
   private String uid;
}
